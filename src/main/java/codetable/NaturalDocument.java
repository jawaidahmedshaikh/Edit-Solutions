/*
 * User: dlataill
 * Date: Aug 5, 2004
 * Time: 12:42:06 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package codetable;

import businesscalendar.BusinessCalendar;
import businesscalendar.BusinessDay;
import client.ClientAddress;
import client.ClientDetail;
import contract.ContractClient;
import contract.Segment;
import contract.dm.composer.VOComposer;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.vo.*;
import event.EDITTrx;
import event.dm.composer.ClientComposer;
import event.dm.composer.EDITTrxComposer;
import event.dm.dao.DAOFactory;
import event.dm.dao.OverdueChargeDAO;
import event.*;
import event.business.Event;
import event.component.EventComponent;
import contract.dm.composer.VOComposer;
import contract.*;
import businesscalendar.BusinessCalendar;
import businesscalendar.BusinessDay;
import fission.utility.Util;
import role.ClientRole;

import java.math.BigDecimal;
import java.util.*;
import engine.FilteredFund;
import engine.Fund;
import group.ContractGroup;


public abstract class NaturalDocument extends PRASEDocument
{
    private ContractSetupVO contractSetupVO = null;
    private ClientSetupVO clientSetupVO = null;
    private List investmentVOInclusionList = new ArrayList();
    private InvestmentVO[] investmentVO = null;
    private InvestmentAllocationOverrideVO[] investmentAllocationOverrideVO = null;
    private ClientVO clientVO = null;

    protected NaturalDocVO naturalDocVO;
    protected GroupSetupVO groupSetupVO = null;
    protected EDITTrxVO editTrxVO = null;
    protected SegmentVO segmentVO = null;
    protected List editTrxVOInclusionList = new ArrayList();
    protected List segmentVOInclusionList = new ArrayList();
    protected CommissionVO[] commissionVO = null;
    protected List clientVOArray = new ArrayList();
    protected List riderClientVOArray = new ArrayList();

    public NaturalDocument()
    {
        naturalDocVO = new NaturalDocVO();
    }

    /**
     * Build NaturalDocVO properties common for all uses of NaturalDoc.
     */
    public void buildDocument()
    {
        investmentAllocationOverrideVO = null;

        try
        {
            segmentVO = super.getSegmentVO(contractSetupVO.getSegmentFK(), segmentVOInclusionList);

            if (segmentVO.getSegmentFK() != 0)
            {
                segmentVO = super.getSegmentVO(segmentVO.getSegmentFK(), segmentVOInclusionList);
            }

            investmentAllocationOverrideVO = contractSetupVO.getInvestmentAllocationOverrideVO();
            if (investmentAllocationOverrideVO == null || investmentAllocationOverrideVO.length == 0)
            {
                investmentAllocationOverrideVO = DAOFactory.getInvestmentAllocationOverrideDAO().findByContractSetupPK(contractSetupVO.getContractSetupPK());
            }

            List investmentVOInclusionList = new ArrayList();
            investmentVOInclusionList.add(InvestmentVO.class);
            investmentVOInclusionList.add(InvestmentAllocationVO.class);
            investmentVOInclusionList.add(BucketVO.class);
            investmentVOInclusionList.add(BucketAllocationVO.class);
            investmentVOInclusionList.add(AnnualizedSubBucketVO.class);
            investmentVO = super.getInvestments(contractSetupVO.getSegmentFK(),  investmentAllocationOverrideVO, investmentVOInclusionList, editTrxVO.getTransactionTypeCT());

            if (investmentVO != null)
            {
                super.sortBuckets(investmentVO, segmentVO, editTrxVO);
            }

            clientVO = new ClientComposer().compose(clientSetupVO);
            clientVOArray.add(clientVO);

            naturalDocVO.addGroupSetupVO(groupSetupVO);

            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("RC") ||
                editTrxVO.getTransactionTypeCT().equalsIgnoreCase("RW"))
            {
                setRMDFields();
            }

            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("MF"))
            {
                setInvestmentArrayVO_GeneralAccountArrayVO();
            }

            if (this.editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_ISSUE))
            {
                setOwnersPremiumTaxState();
            }
	        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("LS") ||
                editTrxVO.getTransactionTypeCT().equalsIgnoreCase("SLS"))
            {
                setDeathInformatonFields();
			}

            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("NT"))
            {
                setNotTakenCumFields(segmentVO.getSegmentPK());
            }

            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("FD"))
            {
                setTamraRetestVO();
            }

            setInsuredFields();

            getTaxDateOfBirth();

			setSecondaryAnnuitantFields();

            initializeLoanSettlementVO();

            if (editTrxVO.getTransactionTypeCT().equals(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT))
            {
                EDITBigDecimal lastLoanCapAmount = super.setLoanCapInfo(segmentVO.getSegmentPK());
                naturalDocVO.setLastLoanCapAmount(lastLoanCapAmount.getBigDecimal());
            }

        }
        catch (Exception e)
        {
            System.out.println("Error building NaturalDocVO. Contract Number: [" + segmentVO.getContractNumber() + "]");

            System.out.println(e);

            e.printStackTrace();
        }
    }


    /**
     * Compose the EDITTrx for the NaturalDocVO using the provided inclusion list and EDITTrxVO.
     * @throws Exception
     */
    protected void composeEDITTrx() throws Exception
    {
        new EDITTrxComposer(editTrxVOInclusionList).compose(editTrxVO);

        clientSetupVO = (ClientSetupVO) editTrxVO.getParentVO(ClientSetupVO.class);
        contractSetupVO = (ContractSetupVO) clientSetupVO.getParentVO(ContractSetupVO.class);
        groupSetupVO = (GroupSetupVO) contractSetupVO.getParentVO(GroupSetupVO.class);

        retainCorrectCharges();
    }

    protected void retainCorrectCharges()
    {
        EDITDate edTrxEffDate = new EDITDate(editTrxVO.getEffectiveDate());

        if (groupSetupVO.getChargeVOCount() > 0)
        {
            List chargesToKeep = new ArrayList();

            ChargeVO[] chargeVOs = groupSetupVO.getChargeVO();
            for (int i = 0; i < chargeVOs.length; i++)
            {
                String oneTimeOnlyInd = chargeVOs[i].getOneTimeOnlyInd();
                if (oneTimeOnlyInd != null && oneTimeOnlyInd.equalsIgnoreCase("Y"))
                {
                    if (chargeVOs[i].getOneTimeOnlyDate() != null &&
                        (new EDITDate(chargeVOs[i].getOneTimeOnlyDate()).equals(edTrxEffDate)))
                    {
                        chargesToKeep.add(chargeVOs[i]);
                    }
                }
                else
                {
                    chargesToKeep.add(chargeVOs[i]);
                }
            }

            groupSetupVO.removeAllChargeVO();

            if (chargesToKeep.size() > 0)
            {
                chargeVOs = (ChargeVO[]) chargesToKeep.toArray(new ChargeVO[chargesToKeep.size()]);
                groupSetupVO.setChargeVO(chargeVOs);
            }
        }
    }
    
    /**
     * Build the CommissionVO for all agent hierarchies on the given SegmentVO.
     * @param segmentVO
     * @throws Exception
     */
    protected void buildCommissionVO(SegmentVO segmentVO) throws Exception
    {
        commissionVO = null;
        String commissionStatus = editTrxVO.getCommissionStatus();

        if (commissionStatus != null && commissionStatus.equalsIgnoreCase("P"))
        {
            AgentHierarchyVO[] agentHierarchyVO = segmentVO.getAgentHierarchyVO();

            if (agentHierarchyVO != null)
            {
                commissionVO = getCommissionData(agentHierarchyVO);
            }
        }
    }

    /**
     * Get the CommissionVO data for the given agent hierarchies.
     * @param agentHierarchyVO
     * @return
     * @throws Exception
     */
    private CommissionVO[] getCommissionData(AgentHierarchyVO[] agentHierarchyVO) throws Exception
    {
        List voInclusionList = new ArrayList();

        voInclusionList.add(AgentVO.class);
        voInclusionList.add(AgentContractVO.class);
        voInclusionList.add(AdditionalCompensationVO.class);
        voInclusionList.add(AgentLicenseVO.class);
        voInclusionList.add(CommissionProfileVO.class);
        
        voInclusionList.add(ClientRoleVO.class);
        voInclusionList.add(ClientRoleFinancialVO.class);
        voInclusionList.add(CheckAdjustmentVO.class);

        List commissions = new ArrayList();

        for (int i = 0; i < agentHierarchyVO.length; i++)
        {
            AgentHierarchyAllocation agentHierarchyAllocation = AgentHierarchyAllocation.findActiveByAgentHierarchyFK_Date(
                    new Long(agentHierarchyVO[i].getAgentHierarchyPK()), new EDITDate(editTrxVO.getEffectiveDate()));

            if (agentHierarchyAllocation != null)  // if trx is back dated, may not have an active allocation for that effective date
            {
                EDITBigDecimal allocationPercent = agentHierarchyAllocation.getAllocationPercent();

                //  Only create commission data for agents whose allocationPercent is not zero
                if (allocationPercent.isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
                {
                    CommissionVO commissionVO = new CommissionVO();

                    //order by hierarchy level
                    AgentSnapshotVO[] agentSnapshotVOs = contract.dm.dao.DAOFactory.getAgentSnapshotDAO().findSortedAgentSnapshotVOs(agentHierarchyVO[i].getAgentHierarchyPK());

                    AgentSnapshotDetailVO[] agentSnapshotDetailVO = new agent.dm.composer.VOComposer().composeCommissionVO(new EDITTrx(editTrxVO), agentSnapshotVOs, agentHierarchyVO[i].getSegmentFK(), voInclusionList);

                    setCorrectCheckAdjustment(agentSnapshotDetailVO);

                    commissionVO.setAllocationPercent( allocationPercent.getBigDecimal() );
                    commissionVO.setAgentSnapshotDetailVO(agentSnapshotDetailVO);
                    commissionVO.setCommissionPK(i + 1);
                    commissions.add(commissionVO);
                }
            }
        }

        return (CommissionVO[]) commissions.toArray(new CommissionVO[commissions.size()]) ;
    }

    private void setCorrectCheckAdjustment(AgentSnapshotDetailVO[] agentSnapshotDetailVO)
    {

        for (int i = 0; i < agentSnapshotDetailVO.length; i++)
        {
            CheckAdjustmentVO[] checkAdjustmentVOs = agentSnapshotDetailVO[i].getAgentVO(0).getCheckAdjustmentVO();
            List checkAdjustmentList = new ArrayList();

            for (int j = 0; j < checkAdjustmentVOs.length; j++)
            {
                agentSnapshotDetailVO[i].getAgentVO(0).removeAllCheckAdjustmentVO();

                String startDateString = checkAdjustmentVOs[j].getStartDate();
                EDITDate startDate = null;
                if (startDateString == null)
                {
                    startDate = new EDITDate(EDITDate.DEFAULT_MIN_DATE);
                }
                else
                {
                    startDate = new EDITDate(startDateString);
                }

                String stopDateString = checkAdjustmentVOs[j].getStopDate();
                EDITDate stopDate = null;
                if (stopDateString == null)
                {
                    stopDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
                }
                else
                {
                    stopDate = new EDITDate(stopDateString);
                }

                EDITDate currentDate = new EDITDate();

                if (checkAdjustmentVOs[j].getAdjustmentCompleteInd() != null &&
                    checkAdjustmentVOs[j].getAdjustmentCompleteInd().equalsIgnoreCase("Y") ||
                    currentDate.before(startDate) && currentDate.after(stopDate))
                {
                    continue;
                }
                else
                {
                    checkAdjustmentList.add(checkAdjustmentVOs[j]);
                }
            }

            if (!checkAdjustmentList.isEmpty())
            {
                agentSnapshotDetailVO[i].getAgentVO(0).setCheckAdjustmentVO((CheckAdjustmentVO[])checkAdjustmentList.toArray(new CheckAdjustmentVO[checkAdjustmentList.size()]));
            }
        }
    }

    /**
     * Build the BaseSegmentVO and RiderSegmentVOs, including the insured client for the 'BI' on both the base and
     * rider segments, and the insured client for the 'IS' and 'MD' transactions on the rider segments.
     * @throws Exception
     */
    protected void composeBaseAndRiderSegmentVOs() throws Exception
    {
        Life life = Life.findBy_SegmentPK(new Long(segmentVO.getSegmentPK()));
        segmentVO.addLifeVO((LifeVO) life.getVO());

        SegmentVO[] riderSegments = segmentVO.getSegmentVO();
        segmentVO.removeAllSegmentVO();

        BaseSegmentVO baseSegmentVO = new BaseSegmentVO();

        if (investmentVO != null)
        {
            segmentVO.setInvestmentVO(investmentVO);
        }

        baseSegmentVO.setSegmentVO(segmentVO);
        baseSegmentVO.setClientVO((ClientVO[])clientVOArray.toArray(new ClientVO[clientVOArray.size()]));

        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("BI"))
        {
            ClientVO baseInsuredClientVO = this.buildInsuredClientVO(segmentVO);
            if (baseInsuredClientVO != null)
            {
                baseSegmentVO.addClientVO(baseInsuredClientVO);
            }
        }

        buildCommissionVO(segmentVO);
        if (commissionVO != null)
        {
            baseSegmentVO.setCommissionVO(commissionVO);
        }
        naturalDocVO.setBaseSegmentVO(baseSegmentVO);

        if (riderSegments != null)
        {
            for (int i = 0; i < riderSegments.length; i++)
            {
                buildRiderClientVOArrayForDataWarehouse(Segment.findByPK(new Long(riderSegments[i].getSegmentPK())).getContractClients());

                InvestmentVO[] riderInvestmentVO = new VOComposer().composeInvestmentVO(riderSegments[i].getSegmentFK(), investmentAllocationOverrideVO, investmentVOInclusionList, editTrxVO.getTransactionTypeCT());

                if (riderInvestmentVO != null)
                {
                    riderSegments[i].setInvestmentVO(riderInvestmentVO);
                }

                RiderSegmentVO riderSegmentVO = new RiderSegmentVO();
                riderSegmentVO.setSegmentVO(riderSegments[i]);
                if (riderClientVOArray.size() > 0)
                {
                    riderSegmentVO.setClientVO((ClientVO[])riderClientVOArray.toArray(new ClientVO[riderClientVOArray.size()]));
                }

                ClientVO riderInsuredClientVO = null;

                if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("BI") ||
                    editTrxVO.getTransactionTypeCT().equalsIgnoreCase("IS") ||
                    editTrxVO.getTransactionTypeCT().equalsIgnoreCase("MD"))
                {
                    riderInsuredClientVO = this.buildInsuredClientVO(riderSegments[i]);
                    if (riderInsuredClientVO != null)
                    {
                        riderSegmentVO.addClientVO(riderInsuredClientVO);
                    }
                }

                CommissionVO[] riderCommissionVO = null;
                String commissionStatus = editTrxVO.getCommissionStatus();

                if (commissionStatus != null && commissionStatus.equalsIgnoreCase("P"))
                {
                    AgentHierarchyVO[] agentHierarchyVO = riderSegments[i].getAgentHierarchyVO();

                    if (agentHierarchyVO != null)
                        riderCommissionVO = getCommissionData(agentHierarchyVO);

                    if (riderCommissionVO != null)
                    {
                        riderSegmentVO.setCommissionVO(riderCommissionVO);
                    }
                }

                naturalDocVO.addRiderSegmentVO(riderSegmentVO);
            }
        }

//        String trxType = editTrxVO.getTransactionTypeCT();
//        if (trxType.equalsIgnoreCase("TF") &&
        if (investmentAllocationOverrideVO != null && investmentAllocationOverrideVO.length > 0)
        {
            int a = 0;
            for (a = 0; a < investmentAllocationOverrideVO.length; a++)
            {
                if (investmentAllocationOverrideVO[a].getToFromStatus().equalsIgnoreCase("F"))
                {
                    naturalDocVO.addInvestmentAllocationOverrideVO(investmentAllocationOverrideVO[a]);
                }
            }
            for (a = 0; a < investmentAllocationOverrideVO.length; a++)
            {
                if (investmentAllocationOverrideVO[a].getToFromStatus().equalsIgnoreCase("T"))
                {
                    naturalDocVO.addInvestmentAllocationOverrideVO(investmentAllocationOverrideVO[a]);
                }
            }
        }
    }

    /**
     * Build the BaseSegmentVO and RiderSegmentVOs
     * @throws Exception
     */
    protected void composeBaseAndRiderSegmentVOs(Segment segment) throws Exception
    {
        SegmentVO segmentVO = (SegmentVO) segment.getVO();
        if (segment.getLife() != null)
        {
            LifeVO lifeVO = (LifeVO) segment.getLife().getVO();
            segmentVO.addLifeVO(lifeVO);
        }
        else
        {
            throw new Exception("Life Record Not found for SegmentKey " + segment.getSegmentPK());
        }

        Set<Investment> investments = segment.getInvestments();
        Iterator it = investments.iterator();
        while (it.hasNext())
        {
            Investment investment = (Investment) it.next();
            InvestmentVO investmentVO = (InvestmentVO) investment.getVO();
            Set<Bucket> buckets = investment.getBuckets();
            Iterator it2 = buckets.iterator();
            while (it2.hasNext())
            {
                Bucket bucket = (Bucket) it2.next();
                BucketVO bucketVO = (BucketVO) bucket.getVO();
                investmentVO.addBucketVO(bucketVO);
            }
            
            segmentVO.addInvestmentVO(investmentVO);
        }

        BaseSegmentVO baseSegmentVO = new BaseSegmentVO();
        baseSegmentVO.setSegmentVO(segmentVO);

        buildClientVOArrayForDataWarehouse(segment.getContractClients());

        baseSegmentVO.setClientVO((ClientVO[])clientVOArray.toArray(new ClientVO[clientVOArray.size()]));

        naturalDocVO.setBaseSegmentVO(baseSegmentVO);

        Set<Segment> riders = segment.getSegments();
        it = riders.iterator();

        while (it.hasNext())
        {
            Segment rider = (Segment) it.next();

            SegmentVO riderVO = (SegmentVO) rider.getVO();

            buildRiderClientVOArrayForDataWarehouse(rider.getContractClients());

            RiderSegmentVO riderSegmentVO = new RiderSegmentVO();
            riderSegmentVO.setSegmentVO(riderVO);
            if (riderClientVOArray.size() > 0)
            {
                riderSegmentVO.setClientVO((ClientVO[])riderClientVOArray.toArray(new ClientVO[riderClientVOArray.size()]));
            }

            naturalDocVO.addRiderSegmentVO(riderSegmentVO);
        }
    }

    private void buildClientVOArrayForDataWarehouse(Set<ContractClient> contractClients) throws Exception
    {
        int clientCount = 0;

        Iterator it = contractClients.iterator();
        while (it.hasNext())
        {
            clientCount += 1;
            ContractClient contractClient = (ContractClient) it.next();

            clientVO = new ClientComposer().compose(contractClient, clientCount);
            clientVOArray.add(clientVO);
        }
    }

    private void buildRiderClientVOArrayForDataWarehouse(Set<ContractClient> contractClients) throws Exception
    {
        riderClientVOArray = new ArrayList();

        int clientCount = 0;

        if (contractClients != null)
        {
            Iterator it = contractClients.iterator();
            while (it.hasNext())
            {
                clientCount += 1;
                ContractClient contractClient = (ContractClient) it.next();

                clientVO = new ClientComposer().compose(contractClient, clientCount);
                riderClientVOArray.add(clientVO);
            }
        }
    }

    /**
     * Associates billing information for the segment (if any) to the NaturalDocVO.
     */
    protected void associateBillingInfo() throws Exception
    {
        String roleTypeCT = naturalDocVO.getBaseSegmentVO().getClientVO(0).getRoleTypeCT();

        if (roleTypeCT.equals("POR")) // This only applies to 'Payor' role types.
        {
            ContractClientVO contractClientVO = naturalDocVO.getBaseSegmentVO().getClientVO(0).getContractClientVO(0);

//            BillingVO billingVO = getBillingInfoForPayor(contractClientVO);
//            if (billingVO != null)
//            {
//                naturalDocVO.addBillingVO(billingVO);
//            }
        }
        else if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("IS"))
        {
            long segmentPK = naturalDocVO.getBaseSegmentVO().getSegmentVO().getSegmentPK();

            List voInclusionList = new ArrayList();
            voInclusionList.add(ClientRoleVO.class);

            ContractClientVO[] contractClientVOs = new VOComposer().composeContractClientVOBySegmentFK(segmentPK, voInclusionList);
            for (int i = 0; i < contractClientVOs.length; i++)
            {
                ClientRoleVO clientRoleVO = (ClientRoleVO) contractClientVOs[i].getParentVO(ClientRoleVO.class);
                if (clientRoleVO.getRoleTypeCT().equalsIgnoreCase("POR"))
                {
//                    BillingVO billingVO = getBillingInfoForPayor(contractClientVOs[i]);
//                    if (billingVO != null)
//                    {
//                        naturalDocVO.addBillingVO(billingVO);
//                    }

                    break;
                }
            }
        }
    }

    /**
     * Retrieves the Billing information for the given payor.
     * @param contractClientVO
     * @return
     * @throws Exception
     */
//    private BillingVO getBillingInfoForPayor(ContractClientVO contractClientVO) throws Exception
//    {
//        BillingVO billingVO = null;
//
//        contract.business.Lookup contractLookup = new contract.component.LookupComponent();
//
//        List voInclusionList = new ArrayList();
//        voInclusionList.add(BillingVO.class);
//
//        BillLapseVO[] billLapseVOs = contractLookup.composeBillLapseVOByContractClientPK(contractClientVO.getContractClientPK(), voInclusionList);
//
//        if (billLapseVOs != null)
//        {
//            BillLapseVO billLapseVO = billLapseVOs[0];
//
//            billingVO = (BillingVO) billLapseVO.getParentVO(BillingVO.class);
//
//            billLapseVO.clearCollections();  // Avoid a possible circular reference (just-in-case).
//
//            billingVO.addBillLapseVO(billLapseVO); // Reorganize from the perspective of the BillingVO since that is what NaturalDoc wants.
//
//        }
//
//        return billingVO;
//    }

    /**
     * Clears the Inclusion List for the EDITTrx Composer - actual VO Inclusions are set in child classes.
     */
    protected void setEDITTrxInclusionList()
    {
        editTrxVOInclusionList.clear();
    }

    /**
     * Clears the Inclusion List for the Segment Composer - actual VO Inclusions are set in child classes.
     */
    protected void setSegmentInclusionList()
    {
        segmentVOInclusionList.clear();
    }

    /**
     * Sets the group rate and group number of participants fields in the natural doc based on information retrieved
     * from the policyGroupSpecification for the given segment.
     * @throws Exception
     */
    protected void setGroupInfo() throws Exception
    {
        if (segmentVO.getParentVOs() != null)
        {
            ContractGroup thisCase = ContractGroup.findBy_ContractGroupPK(new Long(segmentVO.getContractGroupFK()));

            if (thisCase != null)
            {
//                naturalDocVO.setGroupRate(thisCase.getGroupRate().getBigDecimal());
                naturalDocVO.setGroupNoParticipants(0);
            }
        }
    }

    /**
     * Establishes GroupSetup information (including ContractSetup, ClientSetup, and EDITTrx) for the Natural Doc.
     * @param groupSetupVO
     */
    protected void setGroupSetupVO(GroupSetupVO groupSetupVO)
    {
        this.groupSetupVO = groupSetupVO;
        contractSetupVO = groupSetupVO.getContractSetupVO(0);
        clientSetupVO = contractSetupVO.getClientSetupVO(0);
        editTrxVO = clientSetupVO.getEDITTrxVO(0);
    }

    /**
     * Returns the InvestmentAllocationOverrideVOs retrieved for a given segment/transaction.
     * @return
     */
    public InvestmentAllocationOverrideVO[] getInvestmentAllocationOverrideVO()
    {
        return investmentAllocationOverrideVO;
    }

    /**
     * Returns the InvestmentVOs retrieved for a given segment.
     * @return
     */
    public InvestmentVO[] getInvestmentVO()
    {
        return investmentVO;
    }

    /**
     * Returns the CommissionVOs composed for a given segment and its agent hierarchies.
     * @return
     */
    public CommissionVO[] getCommissionVO()
    {
        return commissionVO;
    }

    /**
     * Returns the ClientVO build for a give base segment.
     * @return
     */
    public ClientVO getClientVO()
    {
        return clientVO;
    }

    /**
     * Returns the SegmentVO of the base segment.
     * @return
     */
    public SegmentVO getSegmentVO()
    {
        return segmentVO;
    }

    /**
     * Returns the composed NaturalDocVO.
     * @return
     */
    public VOObject getDocumentAsVO()
    {
        return naturalDocVO;
    }

    /**
     * EDITTrxHistory must be accessed for certain transactions to accumulate certain factors
     */
    public void checkForTransactionAccumGeneration()
    {
        String trxType = editTrxVO.getTransactionTypeCT();

        HashMap trxAccums = new HashMap();

        trxAccums = super.getTransactionAccumFields(trxType, editTrxVO, segmentVO);
        setAccumsInDocument(trxAccums);

    }

    /**
     * For the Accumulations performed now update the Natural Doc.  If the fields were null, zeros were
     * set into its value.
     * @param trxAccums
     * @param trxType
     */
    private void setAccumsInDocument(HashMap trxAccums)
    {
        naturalDocVO.setPremiumToDate((BigDecimal)trxAccums.get("PremiumToDate"));

        naturalDocVO.setPremiumYearToDate((BigDecimal)trxAccums.get("PremiumYearToDate"));

        naturalDocVO.setPremiumCalYearToDate((BigDecimal)trxAccums.get("PremiumCalYearToDate"));

        naturalDocVO.setNetWithdrawalsToDate((BigDecimal)trxAccums.get("WithDToDate"));

        naturalDocVO.setNetWithdrawalsYearToDate((BigDecimal)trxAccums.get("NetWithDYearToDate"));

        naturalDocVO.setCumInitialPremium((BigDecimal)trxAccums.get("CumInitialPrem"));

        naturalDocVO.setCum1035Premium((BigDecimal)trxAccums.get("Cum1035Prem"));

        naturalDocVO.setPremiumSinceLast7PayDate((BigDecimal)trxAccums.get("PremSinceLast7Pay"));

        naturalDocVO.setWithdrawalsSinceLast7PayDate((BigDecimal)trxAccums.get("WithDSinceLast7Pay"));

        naturalDocVO.setNumberTransfersPolYearToDate(((Integer)trxAccums.get("NumberWithDToDate")).intValue());

        naturalDocVO.setNumberWithdrawalsPolYearToDate(((Integer)trxAccums.get("NumberTransfersToDate")).intValue());
    }

    private void setRMDFields()
    {
        try
        {
            int taxYear = editTrxVO.getTaxYear();
            event.business.Event eventComponent = new event.component.EventComponent();
            EDITBigDecimal withdrawalsTaxYearToDate = eventComponent.getWithdrawalGrossAmountTaxYearToDate(taxYear, contractSetupVO.getSegmentFK());
            EDITBigDecimal rmdsTaxYearToDate = eventComponent.getRMDGrossAmountTaxYearToDate(taxYear, contractSetupVO.getSegmentFK());
            EDITBigDecimal accumulatedValue = eventComponent.getPriorCYAccumulatedValue(contractSetupVO.getSegmentFK());

            naturalDocVO.setWithdrawalsTaxYTD(withdrawalsTaxYearToDate.getBigDecimal());
            naturalDocVO.setRMDTaxYTD(rmdsTaxYearToDate.getBigDecimal());
            naturalDocVO.setPriorCYAccumulatedValue(accumulatedValue.getBigDecimal());
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();

        }
    }

    private void setDeathInformatonFields()
    {
        DeathInformationVO deathInformationVO = super.getDeathInformationFromClient(segmentVO.getSegmentPK(), segmentVO.getSegmentNameCT());
        naturalDocVO.addDeathInformationVO(deathInformationVO);
    }

    /**
     * Sets the CumPremiumTax, CumDacTax, CumSalesLoad, CumCoi and SettledOverdueCoi fields - used for Return of Investment Value
     * Values are the sum total of PremiumTax, DacTax, SalesLoad, and Coi ChargeHistories (respectively).
     * @param segmentPK
     */
    private void setNotTakenCumFields(long segmentPK)
    {
        EDITBigDecimal cumPremiumTax = new EDITBigDecimal();
        EDITBigDecimal cumDacTax = new EDITBigDecimal();
        EDITBigDecimal cumSalesLoad = new EDITBigDecimal();
        EDITBigDecimal cumCoi = new EDITBigDecimal();
        EDITBigDecimal settledOverdueCoi = new EDITBigDecimal();

        //Get all Charge Histories for the given contract (specified by segmentPK)
        ChargeHistory[] chargeHistories = ChargeHistory.findBySegmentFK(new Long(segmentPK));
        if (chargeHistories != null)
        {
            for (int i = 0; i < chargeHistories.length; i++)
            {
                if (chargeHistories[i].getChargeTypeCT().equalsIgnoreCase("PremiumTax"))
                {
                    cumPremiumTax = cumPremiumTax.addEditBigDecimal(chargeHistories[i].getChargeAmount());
                }
                else if (chargeHistories[i].getChargeTypeCT().equalsIgnoreCase("DacTax"))
                {
                    cumDacTax = cumDacTax.addEditBigDecimal(chargeHistories[i].getChargeAmount());
                }
                else if (chargeHistories[i].getChargeTypeCT().equalsIgnoreCase("SalesLoad"))
                {
                    cumSalesLoad = cumSalesLoad.addEditBigDecimal(chargeHistories[i].getChargeAmount());
                }
                else if (chargeHistories[i].getChargeTypeCT().equalsIgnoreCase("Coi"))
                {
                    cumCoi = cumCoi.addEditBigDecimal(chargeHistories[i].getChargeAmount());
                }

                EDITTrxHistory editTrxHistory = chargeHistories[i].getEDITTrxHistory();
                EDITTrx editTrx = editTrxHistory.getEDITTrx();
                if (editTrx.getTransactionTypeCT().equalsIgnoreCase("PY"))
                {
                    if (chargeHistories[i].getChargeTypeCT().equalsIgnoreCase("OverdueCoi"))
                    {
                        settledOverdueCoi = settledOverdueCoi.addEditBigDecimal(chargeHistories[i].getChargeAmount());
                    }
                }
            }
        }

        naturalDocVO.setCumPremiumTax(cumPremiumTax.getBigDecimal());
        naturalDocVO.setCumDacTax(cumDacTax.getBigDecimal());
        naturalDocVO.setCumSalesLoad(cumSalesLoad.getBigDecimal());
        naturalDocVO.setCumCoi(cumCoi.getBigDecimal());
        naturalDocVO.setSettledOverdueCoi(settledOverdueCoi.getBigDecimal());
    }

    /**
     * Creates the InvestmentArrayVOs for the MF Transaction
     * @throws Exception
     */ 
    private void setInvestmentArrayVO_GeneralAccountArrayVO() throws Exception
    {
        EDITDate currMFEffDate = new EDITDate(editTrxVO.getEffectiveDate());
        EDITDate priorMFEffDate = getPriorMFEffDate(currMFEffDate);
        EDITDate editTrxFromDate = priorMFEffDate;
        EDITDate edPolEffDate = new EDITDate(segmentVO.getEffectiveDate());

        if (new EDITDate(priorMFEffDate).before(edPolEffDate))
        {
            editTrxFromDate = new EDITDate(segmentVO.getEffectiveDate());
            editTrxFromDate = editTrxFromDate.subtractDays(1);
            priorMFEffDate = editTrxFromDate;
        }

        EDITDate edPriorMFDate = new EDITDate(priorMFEffDate);

        BusinessDay[] businessDays = BusinessDay.findBy_Range_Inclusive(priorMFEffDate, currMFEffDate);

        EDITTrxHistoryVO[] editTrxHistoryVOs = retrieveEDITTrxHistoryVOs(currMFEffDate,
                                                                         editTrxFromDate);

        InvestmentVO[] allInvestments = retrieveAllInvestmentsForSegment();

        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        Map invArrayVOMap = new TreeMap();

        for (int i = 0; i < allInvestments.length; i++)
        {
            long filteredFundFK = allInvestments[i].getFilteredFundFK();

            boolean skipInvestment = false;

            FilteredFund filteredFund = FilteredFund.findByPK(new Long(filteredFundFK));
            Fund fund = filteredFund.getFund();

            //For Loan funds now build and set the GeneralAccountArrayVO
            if (fund.getLoanQualifierCT() != null && fund.getLoanQualifierCT().equalsIgnoreCase(Fund.NONPREFRRED_LOAN_QUALIFIER))
            {
                GeneralAccountArrayVO[] generalAccountArrayVOs = super.createGeneralAccountArrayVO(businessDays, editTrxHistoryVOs, allInvestments[i], edPriorMFDate);
                naturalDocVO.setGeneralAccountArrayVO(generalAccountArrayVOs);
                skipInvestment = true;
            }

            //If contract is "Surrendered" or "FSHedgeFundPend", do not include the holding account
            //in the investmentArray (the holding account will have a Fund.TypeCode value of "System"
            if (segmentVO.getSegmentStatusCT().equalsIgnoreCase("Surrendered") ||
                segmentVO.getSegmentStatusCT().equalsIgnoreCase("FSHedgeFundPend"))
            {
                if (fund.getTypeCodeCT().equalsIgnoreCase("System"))
                {
                    skipInvestment = true;
                }
            }

            if (!skipInvestment)
            {
                BucketVO[] bucketVOs = allInvestments[i].getBucketVO();
                EDITDate priorUVEffDate = null;
                EDITBigDecimal priorCumUnits = new EDITBigDecimal();
                long priorChargeCodeFK = 0;

                Hashtable bucketsUsed = new Hashtable();

                for (int j = 0; j < businessDays.length; j++)
                {
                    String businessDate = businessDays[j].getBusinessDate().getFormattedDate();
                    EDITDate edBusinessDate = new EDITDate(businessDate);
                    if (j == 0 && !edBusinessDate.equals(edPriorMFDate))
                    {
                        InvestmentArrayVO investmentArrayVO = new InvestmentArrayVO();
                        investmentArrayVO.setUnitValueDate(priorMFEffDate.getFormattedDate());
                        investmentArrayVO.setInvestmentFK(allInvestments[i].getInvestmentPK());
                        investmentArrayVO.setCumUnits(new EDITBigDecimal().getBigDecimal());
                        investmentArrayVO.setUnitValue(new EDITBigDecimal().getBigDecimal());

                        invArrayVOMap.put(investmentArrayVO.getInvestmentFK() + "_" + investmentArrayVO.getUnitValueDate(), investmentArrayVO);
                        priorUVEffDate = edPriorMFDate;
                    }

                    UnitValuesVO[] unitValuesVO = null;

                    EDITBigDecimal unitValue = new EDITBigDecimal();

                    boolean exactMatchFound = false;
                    boolean investmentFoundOnTrx = false;

                    EDITBigDecimal cumUnits = new EDITBigDecimal();

                    if (editTrxHistoryVOs != null)
                    {
                        for (int l = 0; l < editTrxHistoryVOs.length; l++)
                        {
                            long chargeCodeFK = 0;
                            investmentFoundOnTrx = false;
                            InvestmentHistoryVO[] investmentHistoryVOs = editTrxHistoryVOs[l].getInvestmentHistoryVO();
                            investmentHistoryVOs = (InvestmentHistoryVO[]) Util.invertObjects(Util.sortObjects(investmentHistoryVOs, new String[] {"getToFromStatus"}));
                            for (int m = 0; m < investmentHistoryVOs.length; m++)
                            {
                                if (investmentHistoryVOs[m].getInvestmentFK() == allInvestments[i].getInvestmentPK())
                                {
                                    chargeCodeFK = investmentHistoryVOs[m].getChargeCodeFK();
                                    investmentFoundOnTrx = true;
                                    m = investmentHistoryVOs.length;
                                }
                            }

                            if (investmentFoundOnTrx)
                            {
                                unitValuesVO = engineLookup.getUnitValuesByFund_ChargeCode_Date(filteredFundFK,
                                                                                                chargeCodeFK,
                                                                                                businessDate,
                                                                                                "Forward");

                                unitValue = new EDITBigDecimal();

                                if (unitValuesVO != null && unitValuesVO.length > 0)
                                {
                                    unitValue = new EDITBigDecimal(unitValuesVO[0].getUnitValue());
                                }

                                EDITTrxVO editTrxVO = (EDITTrxVO) editTrxHistoryVOs[l].getParentVO(EDITTrxVO.class);
                                EDITDate edTrxEffDate = new EDITDate(editTrxVO.getEffectiveDate());
                                if (priorUVEffDate != null)
                                {
                                    EDITDate effectiveDate = new EDITDate(editTrxVO.getEffectiveDate());

                                    if ((effectiveDate.after(priorUVEffDate) || effectiveDate.equals(priorUVEffDate))
                                            && effectiveDate.before(edBusinessDate))
                                    {
                                        EDITDate nextUVEffDate = priorUVEffDate;
                                        int noDaysToGenerate = edTrxEffDate.getElapsedDays(priorUVEffDate) - 1;

                                        for (int m = 0; m < noDaysToGenerate; m++)
                                        {
                                            nextUVEffDate.addDays(1);

                                            InvestmentArrayVO nonBizDayInvArrayVO = new InvestmentArrayVO();
                                            nonBizDayInvArrayVO.setInvestmentFK(allInvestments[i].getInvestmentPK());
                                            nonBizDayInvArrayVO.setUnitValueDate(nextUVEffDate.getFormattedDate());
                                            nonBizDayInvArrayVO.setCumUnits(priorCumUnits.getBigDecimal());
                                            nonBizDayInvArrayVO.setUnitValue(unitValue.getBigDecimal());

                                            invArrayVOMap.put(nonBizDayInvArrayVO.getInvestmentFK() + "_" + nonBizDayInvArrayVO.getUnitValueDate(), nonBizDayInvArrayVO);
                                        }

                                        BucketHistoryVO[] bucketHistoryVOs = editTrxHistoryVOs[l].getBucketHistoryVO();

                                        cumUnits = getCumUnitsForInvestmentArray(bucketHistoryVOs, bucketVOs, bucketsUsed);

                                        InvestmentArrayVO investmentArrayVO = new InvestmentArrayVO();
                                        investmentArrayVO.setInvestmentFK(allInvestments[i].getInvestmentPK());
                                        investmentArrayVO.setUnitValueDate(edTrxEffDate.getFormattedDate());
                                        investmentArrayVO.setCumUnits(cumUnits.getBigDecimal());
                                        investmentArrayVO.setUnitValue(unitValue.getBigDecimal());

                                        invArrayVOMap.put(investmentArrayVO.getInvestmentFK() + "_" + investmentArrayVO.getUnitValueDate(), investmentArrayVO);

                                        priorUVEffDate = edTrxEffDate;
                                        priorCumUnits = cumUnits;
                                        priorChargeCodeFK = chargeCodeFK;
                                    }
                                }
                                else
                                {
                                    if (edTrxEffDate.before(edBusinessDate))
                                    {
                                        BucketHistoryVO[] bucketHistoryVOs = editTrxHistoryVOs[l].getBucketHistoryVO();
                                        cumUnits = getCumUnitsForInvestmentArray(bucketHistoryVOs, bucketVOs, bucketsUsed);

                                        InvestmentArrayVO investmentArrayVO = new InvestmentArrayVO();
                                        investmentArrayVO.setInvestmentFK(allInvestments[i].getInvestmentPK());
                                        investmentArrayVO.setUnitValueDate(edTrxEffDate.getFormattedDate());
                                        investmentArrayVO.setCumUnits(cumUnits.getBigDecimal());
                                        investmentArrayVO.setUnitValue(unitValue.getBigDecimal());

                                        invArrayVOMap.put(investmentArrayVO.getInvestmentFK() + "_" + investmentArrayVO.getUnitValueDate(), investmentArrayVO);

                                        priorUVEffDate = edTrxEffDate;
                                        priorCumUnits = cumUnits;
                                        priorChargeCodeFK = chargeCodeFK;
                                     }
                                }

                                if (edTrxEffDate.equals(edBusinessDate))
                                {
                                    exactMatchFound = true;

                                    if (priorUVEffDate != null)
                                    {
                                        EDITDate nextUVEffDate = priorUVEffDate;
                                        int noDaysToGenerate = edTrxEffDate.getElapsedDays(priorUVEffDate) - 1;

                                        if (noDaysToGenerate > 0)
                                        {
                                            for (int m = 0; m < noDaysToGenerate; m++)
                                            {
                                                nextUVEffDate.addDays(1);

                                                InvestmentArrayVO nonBizDayInvArrayVO = new InvestmentArrayVO();
                                                nonBizDayInvArrayVO.setInvestmentFK(allInvestments[i].getInvestmentPK());
                                                nonBizDayInvArrayVO.setUnitValueDate(nextUVEffDate.getFormattedDate());
                                                nonBizDayInvArrayVO.setCumUnits(priorCumUnits.getBigDecimal());
                                                nonBizDayInvArrayVO.setUnitValue(unitValue.getBigDecimal());

                                                invArrayVOMap.put(nonBizDayInvArrayVO.getInvestmentFK() + "_" + nonBizDayInvArrayVO.getUnitValueDate(), nonBizDayInvArrayVO);
                                            }
                                        }
                                    }

                                    BucketHistoryVO[] bucketHistoryVOs = editTrxHistoryVOs[l].getBucketHistoryVO();
                                    cumUnits = getCumUnitsForInvestmentArray(bucketHistoryVOs, bucketVOs, bucketsUsed);

                                    InvestmentArrayVO investmentArrayVO = new InvestmentArrayVO();
                                    investmentArrayVO.setInvestmentFK(allInvestments[i].getInvestmentPK());
                                    investmentArrayVO.setUnitValueDate(businessDate);
                                    investmentArrayVO.setCumUnits(cumUnits.getBigDecimal());
                                    investmentArrayVO.setUnitValue(unitValue.getBigDecimal());

                                    invArrayVOMap.put(investmentArrayVO.getInvestmentFK() + "_" + investmentArrayVO.getUnitValueDate(), investmentArrayVO);

                                    priorUVEffDate = edBusinessDate;
                                    priorCumUnits = cumUnits;
                                    priorChargeCodeFK = chargeCodeFK;
                                }
                            }
                        }

                        if (!exactMatchFound)
                        {

                            unitValuesVO = engineLookup.getUnitValuesByFund_ChargeCode_Date(filteredFundFK,
                                                                                            priorChargeCodeFK,
                                                                                            businessDate,
                                                                                            "Forward");

                            unitValue = new EDITBigDecimal();

                            if (unitValuesVO != null && unitValuesVO.length > 0)
                            {
                                unitValue = new EDITBigDecimal(unitValuesVO[0].getUnitValue());
                            }

                            InvestmentArrayVO investmentArrayVO = new InvestmentArrayVO();
                            investmentArrayVO.setInvestmentFK(allInvestments[i].getInvestmentPK());
                            investmentArrayVO.setUnitValueDate(businessDate);
                            investmentArrayVO.setCumUnits(priorCumUnits.getBigDecimal());
                            investmentArrayVO.setUnitValue(unitValue.getBigDecimal());

                            invArrayVOMap.put(investmentArrayVO.getInvestmentFK() + "_" + investmentArrayVO.getUnitValueDate(), investmentArrayVO);
                            priorUVEffDate = edBusinessDate;
                        }
                    }
                }
            }
        }

        if (!invArrayVOMap.isEmpty())
        {
            Set keys = invArrayVOMap.keySet();
            Iterator it = keys.iterator();
            List invArrayVOList = new ArrayList();
            while (it.hasNext())
            {
                String mapKey = (String) it.next();
                invArrayVOList.add(invArrayVOMap.get(mapKey));
            }

            naturalDocVO.setInvestmentArrayVO((InvestmentArrayVO[]) invArrayVOList.toArray(new InvestmentArrayVO[invArrayVOMap.size()]));
        }
    }

    /**
     * Calculates the effective date of the prior MF transaction
     * @param currMFEffDate
     * @return
     */ 
    private EDITDate getPriorMFEffDate(EDITDate currMFEffDate)
    {
        EDITDate edPriorMFEffDate = currMFEffDate.subtractMonths(1);
        edPriorMFEffDate = edPriorMFEffDate.getEndOfMonthDate();

        BusinessCalendar businessCalendar = new BusinessCalendar();
        BusinessDay businessDay = businessCalendar.getBestBusinessDay(edPriorMFEffDate);

        edPriorMFEffDate = businessDay.getBusinessDate();

        return edPriorMFEffDate;
    }

    /**
     * Retrieve the EDITTrxHistory records to be used in creation of the InvestmentArrayVO
     * @param segmentPK
     * @param currMFEffDate
     * @param priorMFEffDate
     * @param edPolEffDate
     * @return
     * @throws Exception
     */
    private EDITTrxHistoryVO[] retrieveEDITTrxHistoryVOs(EDITDate currMFEffDate,
                                                         EDITDate priorMFEffDate) throws Exception
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(EDITTrxVO.class);
        voInclusionList.add(BucketHistoryVO.class);
        voInclusionList.add(InvestmentHistoryVO.class);

        EDITTrxHistoryVO[] editTrxHistoryVOs = null;

        event.business.Event eventComponent = new event.component.EventComponent();

        editTrxHistoryVOs = eventComponent.composeEDITTrxHistoryByEffectiveDate(segmentVO.getSegmentPK(),
                                                                                priorMFEffDate.getFormattedDate(),
                                                                                currMFEffDate.getFormattedDate(),
                                                                                "MF",
                                                                                voInclusionList);

        if (editTrxHistoryVOs != null)
        {
            editTrxHistoryVOs = (EDITTrxHistoryVO[])Util.sortObjects(editTrxHistoryVOs, new String[] {"getEDITTrxHistoryPK"});
        }

        return editTrxHistoryVOs;
    }

    private InvestmentVO[] retrieveAllInvestmentsForSegment() throws Exception
    {
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.clear();
        voInclusionList.add(BucketVO.class);

        return contractLookup.composeInvestmentVOBySegmentPK(segmentVO.getSegmentPK(), voInclusionList);
    }

    /**
     * Sums up the cumUnits for all of the given bucketHistory records 
     * @param bucketHistoryVOs
     * @param bucketVOs
     * @return
     */ 
    private EDITBigDecimal getCumUnitsForInvestmentArray(BucketHistoryVO[] bucketHistoryVOs,
                                                         BucketVO[] bucketVOs,
                                                         Hashtable bucketsUsed)
    {
        EDITBigDecimal cumUnits = new EDITBigDecimal();
        List bucketFKs = new ArrayList();
        for (int i = 0; i < bucketHistoryVOs.length; i++)
        {
            long bucketFK = bucketHistoryVOs[i].getBucketFK();
            for (int j = 0; j < bucketVOs.length; j++)
            {
                if (bucketFK == bucketVOs[j].getBucketPK())
                {
                    bucketFKs.add(bucketFK + "");
                    cumUnits = cumUnits.addEditBigDecimal(bucketHistoryVOs[i].getCumUnits());
                    bucketsUsed.put(bucketFK + "", new EDITBigDecimal(bucketHistoryVOs[i].getCumUnits()));
                }
            }
        }

        Enumeration bucketKeys = bucketsUsed.keys();
        boolean bucketFound = false;
        while(bucketKeys.hasMoreElements())
        {
            bucketFound = false;
            String bucketKey = (String) bucketKeys.nextElement();
            for (int i = 0; i < bucketFKs.size(); i++)
            {
                if (bucketFKs.get(i).equals(bucketKey))
                {
                    bucketFound = true;
                    i = bucketFKs.size();
                }
            }

            if (!bucketFound)
            {
                cumUnits = cumUnits.addEditBigDecimal((EDITBigDecimal) bucketsUsed.get(bucketKey));
            }
        }

        return cumUnits;
    }

    protected void composeOverdueChargesRemaining() throws Exception
    {
        long segmentPK = segmentVO.getSegmentPK();

        // these are the transactions that we need to retrieve overdue chages against
        String[] transactionTypes = new String[] {"MD", "ML"};

        OverdueChargeVO[] overdueChargeVOs = null;

        // retrieve overdueCharges fot the following transactions

        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("PY") ||
            editTrxVO.getTransactionTypeCT().equalsIgnoreCase("MD") ||
            editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN) ||
            editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT))
        {
            overdueChargeVOs = new OverdueChargeDAO().findBySegmentPK_AND_TransactionType(segmentPK, transactionTypes, editTrxVO.getEffectiveDate());
        }

        if (overdueChargeVOs != null)
        {
            for (int i = 0; i < overdueChargeVOs.length; i++)
            {
                long overdueChargePK = overdueChargeVOs[i].getOverdueChargePK();
                EDITBigDecimal overdueCoi = new EDITBigDecimal(overdueChargeVOs[i].getOverdueCoi());
                EDITBigDecimal overdueAdmin = new EDITBigDecimal(overdueChargeVOs[i].getOverdueAdmin());
                EDITBigDecimal overdueExpense = new EDITBigDecimal(overdueChargeVOs[i].getOverdueExpense());
                EDITBigDecimal overdueCollateralization = new EDITBigDecimal(overdueChargeVOs[i].getOverdueCollateralization());

                //now subtract any settled charges from the OverdueCharge fields to get the remaining overdue amounts (if any).
                if (overdueChargeVOs[i].getOverdueChargeSettledVOCount() > 0)
                {
                    Event eventComponent = new EventComponent();

                    OverdueChargeSettledVO[] overdueChargeSettledVOs = overdueChargeVOs[i].getOverdueChargeSettledVO();
                    for (int j = 0; j < overdueChargeSettledVOs.length; j++)
                    {
                        EDITTrxVO editTrxForSettledCharges = eventComponent.composeEDITTrxVOByEDITTrxPK(overdueChargeSettledVOs[j].getEDITTrxFK(), new ArrayList());
                        if (editTrxForSettledCharges.getStatus().equalsIgnoreCase("N") ||
                            editTrxForSettledCharges.getStatus().equalsIgnoreCase("A"))
                        {
                            overdueCoi = overdueCoi.subtractEditBigDecimal(new EDITBigDecimal(overdueChargeSettledVOs[j].getSettledCoi()));
                            overdueAdmin = overdueAdmin.subtractEditBigDecimal(new EDITBigDecimal(overdueChargeSettledVOs[j].getSettledAdmin()));
                            overdueExpense = overdueExpense.subtractEditBigDecimal(new EDITBigDecimal(overdueChargeSettledVOs[j].getSettledExpense()));
                            overdueCollateralization = overdueCollateralization.subtractEditBigDecimal(new EDITBigDecimal(overdueChargeSettledVOs[j].getSettledCollateralization()));
                        }
                    }
                }

                if (overdueCoi.isGT(new EDITBigDecimal()) ||
                    overdueAdmin.isGT(new EDITBigDecimal()) ||
                    overdueExpense.isGT(new EDITBigDecimal()) ||
                    overdueCollateralization.isGT(new EDITBigDecimal()))
                {
                    OverdueChargeRemainingVO overdueChargeRemainingVO = new OverdueChargeRemainingVO();
                    overdueChargeRemainingVO.setOverdueChargePK(overdueChargePK);
                    overdueChargeRemainingVO.setRemainingCoi(overdueCoi.getBigDecimal());
                    overdueChargeRemainingVO.setRemainingAdmin(overdueAdmin.getBigDecimal());
                    overdueChargeRemainingVO.setRemainingExpense(overdueExpense.getBigDecimal());
                    overdueChargeRemainingVO.setOverdueCollateralization(overdueCollateralization.getBigDecimal());

                    naturalDocVO.addOverdueChargeRemainingVO(overdueChargeRemainingVO);
                }
            }
        }
    }

    /**
     * Populates OwnersPremiumTaxState in NatauralDocVO.
     * Is set only when the transactionTypeCT is 'IS'
     */
    private void setOwnersPremiumTaxState()
    {
        Segment segment = new Segment(segmentVO);

        ClientDetail ownerClientDetail = segment.getOwner();

        ClientAddress ownerPrimaryAddress = ownerClientDetail.getPrimaryAddress();
        if (ownerPrimaryAddress != null)
        {
            naturalDocVO.setOwnersPremiumTaxState(ownerPrimaryAddress.getStateCT());
        }
        else
        {
            ClientAddress ownerBusinessAddress = ownerClientDetail.getBusinessAddress();
            if (ownerBusinessAddress != null)
            {
                naturalDocVO.setOwnersPremiumTaxState(ownerBusinessAddress.getStateCT());
            }
        }
    }

    /**
     * Populates Insured fields.
     */
    private void setInsuredFields()
    {
        Segment segment = new Segment(segmentVO);

        // only life policies will have insured role.
        ClientDetail insured = segment.getInsured();

        if (insured != null)
        {
            naturalDocVO.setInsuredGender(insured.getGenderCT());
            naturalDocVO.setInsuredDateOfBirth(insured.getBirthDate() == null ? null : insured.getBirthDate().getFormattedDate());

            ClientAddress insuredAddress = insured.getPrimaryAddress();

            if (insuredAddress != null)
            {
                naturalDocVO.setInsuredResidenceState(insuredAddress.getStateCT());
            }
        }

        ContractClient insuredContractClient = segment.getInsuredContractClient();

        if (insuredContractClient != null)
        {
            naturalDocVO.setInsuredIssueAge(insuredContractClient.getIssueAge());
            naturalDocVO.setInsuredClass(insuredContractClient.getClassCT());
            naturalDocVO.setInsuredUnderwritingClass(insuredContractClient.getUnderwritingClassCT());
        }

    }


    /**
     * Retrieve the date of birth of the owner (if it is a "natural" client).  If the owner is a
     * Non-natural entity (not Male or Female), the retrieve the Annuitant or Insured (depending on product type)
     * date of birth.
     */
    private void getTaxDateOfBirth()
    {
        Segment segment = new Segment(segmentVO);

        ClientDetail ownerClientDetail = segment.getOwner();

        if (ownerClientDetail.isStatusNatural())
        {
            naturalDocVO.setTaxDateOfBirth(ownerClientDetail.getBirthDate().getFormattedDate());
        }
        else
        {
            ClientDetail annuitantClientDetail = segment.getAnnuitant();

            if (annuitantClientDetail != null)
            {
                naturalDocVO.setTaxDateOfBirth(annuitantClientDetail.getBirthDate().getFormattedDate());
            }
            else
            {
                ClientDetail insuredClientDetail = segment.getInsured();

                if (insuredClientDetail != null)
                {
                    naturalDocVO.setTaxDateOfBirth(insuredClientDetail.getBirthDate().getFormattedDate());
                }
            }
        }
    }

    /**
     * Check the contractClient for the role of Deceased if it exists add it as a clientvo
     */
    private void setDeceasedRole()
    {
        Segment segment = new Segment(segmentVO);

        ContractClient deceasedContractClient = segment.getDeceasedContractClient();

        if (deceasedContractClient != null)
        {
            ClientRole clientRole = deceasedContractClient.getClientRole();
            ClientDetail clientDetail = clientRole.getClientDetail();

            ClientVO deceasedClientVO = new ClientVO();
            deceasedClientVO.addClientDetailVO((ClientDetailVO)clientDetail.getVO());
            deceasedClientVO.addContractClientVO((ContractClientVO)deceasedContractClient.getVO());
            deceasedClientVO.setRoleTypeCT(clientRole.getRoleTypeCT());
            deceasedClientVO.setClientPK(2);

            clientVOArray.add(deceasedClientVO);
        }
    }

    /**
     * The Face Decrease trx needs premium information for Tamra Retest
     */
    private void setTamraRetestVO()
    {
        TamraRetestVO[] tamraRetestVOs = super.getTamraRetestVOs(segmentVO, editTrxVO);

        if (tamraRetestVOs != null)
        {
            naturalDocVO.setTamraRetestVO(tamraRetestVOs);
        }
    }

    /**
     * For joint life payouts get the secondary annuitant info
     */
    private void setSecondaryAnnuitantFields()
    {
       Segment segment = new Segment(segmentVO);
        ClientDetail secondaryAnnuitant = segment.getSecondaryAnnuitant();

        if (secondaryAnnuitant != null)
        {
            naturalDocVO.setSecondaryAnnuitantGender(secondaryAnnuitant.getGenderCT());
        }

        ContractClient SANContractClient = segment.getSANContractClient();

        if (SANContractClient != null)
        {
            naturalDocVO.setSecondaryAnnuitantIssueAge(SANContractClient.getIssueAge());
            naturalDocVO.setSecondaryAnnuitantTableRating(SANContractClient.getTableRatingCT());
        }
	}


    private void initializeLoanSettlementVO()
    {
        LoanSettlementVO loanSettlementVO = new LoanSettlementVO();
        loanSettlementVO.setDollars(new EDITBigDecimal().getBigDecimal());
        loanSettlementVO.setLoanInterestDollars(new EDITBigDecimal().getBigDecimal());
        loanSettlementVO.setLoanInterestLiabilityPaid(new EDITBigDecimal().getBigDecimal());
        loanSettlementVO.setLoanPrincipalDollars(new EDITBigDecimal().getBigDecimal());

        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_DEATH) ||
            editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_REDEMPTION))
        {
            populateLoanSettlementVO(loanSettlementVO);
        }
        else
        {
            naturalDocVO.addLoanSettlementVO(loanSettlementVO);
        }


        EDITTrx editTrx = null;

    }

    /**
     * For trx type of HDTH and HREM check for loan history and populate the settlementVO.  The HREM is
     * only used when genereated from the Full Surrender transaction.
     * @param loanSettlementVO
     */
    private void populateLoanSettlementVO(LoanSettlementVO loanSettlementVO)
    {
        boolean trxIsFS = false;

        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_HF_REDEMPTION))
        {
            trxIsFS = checkOriginatingTrx(editTrxVO.getOriginatingTrxFK());
            populateFromBucketHistory(loanSettlementVO);
        }
        else
        {
            populateFromBucketHistory(loanSettlementVO);
        }

        naturalDocVO.addLoanSettlementVO(loanSettlementVO);
    }

    private void populateFromBucketHistory(LoanSettlementVO loanSettlementVO)
    {
        List voExclusionList = new ArrayList();
        voExclusionList.add(OverdueChargeVO.class);
        voExclusionList.add(OverdueChargeSettledVO.class);
        voExclusionList.add(FinancialHistoryVO.class);
        voExclusionList.add(ChargeHistoryVO.class);
        voExclusionList.add(SegmentHistoryVO.class);
        voExclusionList.add(ReinsuranceHistoryVO.class);
        voExclusionList.add(WithholdingHistoryVO.class);
        voExclusionList.add(CommissionHistoryVO.class);
        voExclusionList.add(InvestmentHistoryVO.class);
        voExclusionList.add(InSuspenseVO.class);
        voExclusionList.add(EDITTrxCorrespondenceVO.class);
        voExclusionList.add(BucketChargeHistoryVO.class);

        EDITTrxVO[] editTrxVOs = EDITTrx.findBySegmentPK_TrxTypes_Status(segmentVO.getSegmentPK(), new String[] {"FS", "DE"}, true, voExclusionList);

        for (int i = 0; i < editTrxVOs.length; i++)
        {
            BucketHistoryVO[] bucketHistoryVOs = editTrxVOs[i].getEDITTrxHistoryVO(0).getBucketHistoryVO();
            EDITBigDecimal accumLoanInterestLiability = new EDITBigDecimal();
            for (int j = 0; j < bucketHistoryVOs.length; j++)
            {
                EDITBigDecimal loanInterestLiability = new EDITBigDecimal(bucketHistoryVOs[j].getLoanInterestLiability());
                accumLoanInterestLiability = accumLoanInterestLiability.addEditBigDecimal(loanInterestLiability);

                EDITBigDecimal loanPrincipalDollars = new EDITBigDecimal(bucketHistoryVOs[j].getLoanPrincipalDollars());
                if (loanPrincipalDollars.isGT("0"))
                {
                    loanSettlementVO.setDollars(bucketHistoryVOs[j].getDollars());
                    loanSettlementVO.setLoanInterestDollars(bucketHistoryVOs[j].getLoanInterestDollars());
                    loanSettlementVO.setLoanPrincipalDollars(bucketHistoryVOs[j].getLoanPrincipalDollars());
                }

            }

            if ((new EDITBigDecimal(loanSettlementVO.getLoanPrincipalDollars())).isGT("0"))
            {
                loanSettlementVO.setLoanInterestLiabilityPaid(accumLoanInterestLiability.getBigDecimal());
            }
        }
    }

    private boolean checkOriginatingTrx(long originatingTrxFK)
    {
        EDITTrxVO editTrxVO = EDITTrx.findByPK_UsingCRUD(originatingTrxFK);

        boolean trxIsFS = false;

        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FULLSURRENDER))
        {
            trxIsFS = true;
        }

        return trxIsFS;
    }
}
