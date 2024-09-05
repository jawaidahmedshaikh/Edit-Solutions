/*
 * User: dlataill
 * Date: Aug 10, 2004
 * Time: 8:40:18 AM
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

import contract.AgentHierarchyAllocation;
import contract.ContractClient;
import contract.Segment;

import contract.dm.composer.VOComposer;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.vo.AdditionalCompensationVO;
import edit.common.vo.AgentContractVO;
import edit.common.vo.AgentHierarchyVO;
import edit.common.vo.AgentLicenseVO;
import edit.common.vo.AgentSnapshotDetailVO;
import edit.common.vo.AgentSnapshotVO;
import edit.common.vo.AgentVO;
import edit.common.vo.AnnualizedSubBucketVO;
import edit.common.vo.BaseSegmentVO;
import edit.common.vo.BillScheduleVO;
import edit.common.vo.BucketAllocationVO;
import edit.common.vo.BucketHistoryVO;
import edit.common.vo.BucketVO;
import edit.common.vo.ChargeHistoryVO;
import edit.common.vo.ChargeVO;
import edit.common.vo.ClientRoleFinancialVO;
import edit.common.vo.ClientRoleVO;
import edit.common.vo.ClientSetupVO;
import edit.common.vo.ClientVO;
import edit.common.vo.CommissionHistoryVO;
import edit.common.vo.CommissionPhaseVO;
import edit.common.vo.CommissionProfileVO;
import edit.common.vo.CommissionVO;
import edit.common.vo.ContractSetupVO;
import edit.common.vo.DeathInformationVO;
import edit.common.vo.DepositsVO;
import edit.common.vo.EDITTrxHistoryVO;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.FinancialHistoryVO;
import edit.common.vo.GeneralAccountArrayVO;
import edit.common.vo.GroupSetupVO;
import edit.common.vo.InSuspenseVO;
import edit.common.vo.InherentRiderVO;
import edit.common.vo.InvestmentAllocationOverrideVO;
import edit.common.vo.InvestmentAllocationVO;
import edit.common.vo.InvestmentArrayVO;
import edit.common.vo.InvestmentHistoryVO;
import edit.common.vo.InvestmentVO;
import edit.common.vo.LifeVO;
import edit.common.vo.OverdueChargeRemainingVO;
import edit.common.vo.OverdueChargeSettledVO;
import edit.common.vo.OverdueChargeVO;
import edit.common.vo.PayoutVO;
import edit.common.vo.PremiumDueVO;
import edit.common.vo.RedoDocVO;
import edit.common.vo.ReinsuranceHistoryVO;
import edit.common.vo.ReinsuranceVO;
import edit.common.vo.RequiredMinDistributionVO;
import edit.common.vo.RiderSegmentVO;
import edit.common.vo.ScheduledEventVO;
import edit.common.vo.SegmentHistoryVO;
import edit.common.vo.SegmentVO;
import edit.common.vo.TamraRetestVO;
import edit.common.vo.UnitValuesVO;
import edit.common.vo.VOObject;
import edit.common.vo.WithholdingHistoryVO;
import edit.common.vo.WithholdingVO;

import edit.services.config.ServicesConfig;

import engine.FilteredFund;
import engine.Fund;

import event.EDITTrx;

import event.business.Event;

import event.component.EventComponent;

import event.dm.composer.EDITTrxHistoryComposer;
import event.dm.composer.GroupSetupComposer;
import event.dm.dao.DAOFactory;
import event.dm.dao.OverdueChargeDAO;

import fission.utility.Util;

import group.ContractGroup;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import reinsurance.ContractTreaty;


public class RedoDocument extends PRASEDocument
{
    private RedoDocVO redoDocVO;
    private EDITTrxVO editTrxVO;
    private SegmentVO segmentVO;

    public RedoDocument(EDITTrxVO editTrxVO)
    {
        redoDocVO = new RedoDocVO();
        this.editTrxVO = editTrxVO;
    }

    /**
     * Build RedoDocVO properties common for all uses of RedoDoc
     */
    public void buildDocument()
    {
        try
        {
            List editTrxHistoryList = new ArrayList();
            editTrxHistoryList.add(FinancialHistoryVO.class);
            editTrxHistoryList.add(ChargeHistoryVO.class);
            editTrxHistoryList.add(WithholdingVO.class);
            editTrxHistoryList.add(InSuspenseVO.class);
            editTrxHistoryList.add(CommissionHistoryVO.class);
//            editTrxHistoryList.add(InvestmentHistoryVO.class);
            editTrxHistoryList.add(WithholdingHistoryVO.class);
            editTrxHistoryList.add(ReinsuranceHistoryVO.class);
            editTrxHistoryList.add(SegmentHistoryVO.class);
//            editTrxHistoryList.add(BucketHistoryVO.class);
//            editTrxHistoryList.add(BucketChargeHistoryVO.class);

            long editTrxPK = 0;
            long editTrxHistoryPK = 0;

            if (editTrxVO.getPendingStatus().equalsIgnoreCase("F") ||
                editTrxVO.getPendingStatus().equalsIgnoreCase("B"))
            {
                editTrxHistoryList.add(BucketHistoryVO.class);
                editTrxPK = editTrxVO.getEDITTrxPK();
            }
            else
            {
                editTrxPK = editTrxVO.getReapplyEDITTrxFK();
            }

            EDITTrxHistoryVO[] editTrxHistoryVOs = DAOFactory.getEDITTrxHistoryDAO().findByEditTrxPK(editTrxPK);
            if (editTrxHistoryVOs != null)
            {
                editTrxHistoryPK = editTrxHistoryVOs[0].getEDITTrxHistoryPK();
            }

            EDITTrxHistoryComposer editTrxHistoryComposer = new EDITTrxHistoryComposer(editTrxHistoryList);

            EDITTrxHistoryVO editTrxHistoryVO = editTrxHistoryComposer.compose(editTrxHistoryPK);

            GroupSetupVO[] groupSetupVOs = DAOFactory.getGroupSetupDAO().findByEditTrxPK(editTrxPK);

            List voInclusionList = new ArrayList();
            voInclusionList.add(ContractSetupVO.class);
            voInclusionList.add(ChargeVO.class);
            voInclusionList.add(ScheduledEventVO.class);
            voInclusionList.add(ClientSetupVO.class);
            voInclusionList.add(InvestmentAllocationOverrideVO.class);

            GroupSetupComposer groupSetupComposer = new GroupSetupComposer(voInclusionList);

            GroupSetupVO groupSetupVO = groupSetupComposer.compose(groupSetupVOs[0].getGroupSetupPK());

            groupSetupVO = retainCorrectCharges(groupSetupVO);

            ContractSetupVO[] contractSetupVO = groupSetupVO.getContractSetupVO();

            ClientSetupVO[] clientSetupVO = contractSetupVO[0].getClientSetupVO();

            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("MD"))
            {
                OverdueChargeVO[] overdueChargeVO = DAOFactory.getOverdueChargeDAO().findByEDITTrxFK(editTrxVO.getEDITTrxPK());
                if (overdueChargeVO != null && overdueChargeVO.length > 0)
                {
                    editTrxVO.addOverdueChargeVO(overdueChargeVO[0]);
                }
            }

            editTrxVO.addEDITTrxHistoryVO(editTrxHistoryVO);

            clientSetupVO[0].addEDITTrxVO(editTrxVO);

            List segmentVOInclusionList = new ArrayList();
//            segmentVOInclusionList.add(PolicyGroupVO.class);
            segmentVOInclusionList.add(SegmentVO.class);
            segmentVOInclusionList.add(LifeVO.class);
            segmentVOInclusionList.add(PayoutVO.class);
            segmentVOInclusionList.add(AgentHierarchyVO.class);
            segmentVOInclusionList.add(DepositsVO.class);
            segmentVOInclusionList.add(InherentRiderVO.class);
            segmentVOInclusionList.add(PremiumDueVO.class);
            segmentVOInclusionList.add(CommissionPhaseVO.class);
            segmentVOInclusionList.add(BillScheduleVO.class);
            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("RC") ||
                editTrxVO.getTransactionTypeCT().equalsIgnoreCase("RW"))
            {
                segmentVOInclusionList.add(RequiredMinDistributionVO.class);
            }

            segmentVO = new VOComposer().composeSegmentVO(contractSetupVO[0].getSegmentFK(), segmentVOInclusionList);

            // double groupRate = 0;
            // commented above line(s) for double to BigDecimal conversion
            // sprasad 9/27/2004
            EDITBigDecimal groupRate = new EDITBigDecimal();
            int groupNoParticipants = 0;

            ContractGroup thisCase = null;
            long contractGroupFK = segmentVO.getContractGroupFK();
            if (contractGroupFK != 0)
            {
                thisCase = ContractGroup.findBy_ContractGroupPK(new Long(contractGroupFK));
                if (thisCase != null)
                {
//                    groupRate = thisCase.getGroupRate();
                    groupNoParticipants = 0;
                }
            }


            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("RC"))
            {
                event.business.Event eventComponent = new event.component.EventComponent();
                EDITBigDecimal withdrawalsTaxYearToDate = eventComponent.getWithdrawalGrossAmountTaxYearToDate(editTrxVO.getTaxYear(),
                                                                                                               contractSetupVO[0].getSegmentFK());
                EDITBigDecimal rmdsTaxYearToDate = eventComponent.getRMDGrossAmountTaxYearToDate(editTrxVO.getTaxYear(),
                                                                                                 contractSetupVO[0].getSegmentFK());
                EDITBigDecimal accumulatedValue = eventComponent.getPriorCYAccumulatedValue(contractSetupVO[0].getSegmentFK());


                redoDocVO.setWithdrawalsTaxYTD(withdrawalsTaxYearToDate.getBigDecimal());
                redoDocVO.setRMDTaxYTD(rmdsTaxYearToDate.getBigDecimal());
                redoDocVO.setPriorCYAccumulatedValue(accumulatedValue.getBigDecimal());
            }

            redoDocVO.addGroupSetupVO(groupSetupVO);

            // redoDocVO.setGroupRate(groupRate);
            // commented above line(s) for double to BigDecimal conversion
            // sprasad 9/27/2004
            redoDocVO.setGroupRate( groupRate.getBigDecimal() );
            redoDocVO.setGroupNoParticipants(groupNoParticipants);

            composeBaseAndRiderSegmentVOs( segmentVO,
                                            contractSetupVO[0],
                                             contractSetupVO[0].getClientSetupVO(0),
                                              editTrxVO);

            associateBillingInfo();

            associateReinsuranceInformation();

            checkForTransactionAccumGeneration(segmentVO);

            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("MF"))
            {
                setInvestmentArrayVO_GeneralAccountArrayVO(segmentVO);
            }

            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("LS"))
            {
                setDeathInformatonFields(segmentVO);
            }

            getTaxDateOfBirth(segmentVO);

            // retrieve overdueCharges for the contract when the transaction is 'PY'
            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("PY") ||
                editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN))

            {
                composeOverdueChargesRemaining(segmentVO);
            }

            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("FD"))
            {
                setTamraRetestVO(segmentVO);
            }

            prepareDocument();

            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_ISSUE))
            {
                setOwnersPremiumTaxState(segmentVO);
            }

            setInsuredFields(segmentVO);

            if (editTrxVO.getTransactionTypeCT().equals(EDITTrx.TRANSACTIONTYPECT_LOAN_REPAYMENT))
            {
                EDITBigDecimal lastLoanCapAmount = super.setLoanCapInfo(segmentVO.getSegmentPK());
                redoDocVO.setLastLoanCapAmount(lastLoanCapAmount.getBigDecimal());
            }
        }
        catch (Exception e)
        {
//            Logger logger = Logging.getLogger(Logging.LOGGER_CONTRACT_BATCH);
//
//            LogEntryVO logEntryVO = new LogEntryVO();
//
//            logEntryVO.setMessage("Reapply Failed - " + e.getMessage());
//            logEntryVO.setHint("RedoDocVO Was Not Built");
//
//            logger.error(logEntryVO);
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
    }

    protected GroupSetupVO retainCorrectCharges(GroupSetupVO groupSetupVO)
    {
        EDITDate edTrxEffDate = new EDITDate(editTrxVO.getEffectiveDate());

        if (groupSetupVO.getChargeVOCount() > 0)
        {
            ChargeVO[] chargeVOs = groupSetupVO.getChargeVO();
            groupSetupVO.removeAllChargeVO();
            for (int i = 0; i < chargeVOs.length; i++)
            {
                if (chargeVOs[i].getOneTimeOnlyDate() != null &&
                    (new EDITDate(chargeVOs[i].getOneTimeOnlyDate()).after(edTrxEffDate) ||
                     new EDITDate(chargeVOs[i].getOneTimeOnlyDate()).before(edTrxEffDate)))
                {
                }
                else
                {
                    groupSetupVO.addChargeVO(chargeVOs[i]);
                }
            }
        }

        return groupSetupVO;
    }

    /**
     * Transactions that are reinsurable have [possibly] Treaties mapped to the Segment as ContractTreaties. The
     * Reinsurance information is driven by these ContractTreaties.
     */
    public void associateReinsuranceInformation()
    {
        // This is assumed to be the ReinsuranceDocument at the base Segment level only. Riders would have to be added
        // in the future.
        BaseSegmentVO baseSegmentVO = redoDocVO.getBaseSegmentVO();

        Segment segment = new Segment(baseSegmentVO.getSegmentVO());

        ContractTreaty[] contractTreaties = ContractTreaty.findBy_SegmentPK_V1(segment.getSegmentPK());

        if (contractTreaties != null)
        {
            for (int i = 0; i < contractTreaties.length; i++)
            {
                ContractTreaty contractTreaty = contractTreaties[i];

                if (contractTreaty.getStatus() == null)
                {
                    ReinsuranceDocument reinsuranceDocument = PRASEDocumentFactory.getSingleton().getReinsuranceDocument(contractTreaty);

                    reinsuranceDocument.buildDocument();

                    ReinsuranceVO reinsuranceVO = (ReinsuranceVO) reinsuranceDocument.getDocumentAsVO();

                    baseSegmentVO.addReinsuranceVO(reinsuranceVO);
                }
            }
        }
    }

    /**
     * Build the BaseSegmentVO and RiderSegmentVOs, including the insured client for the 'BI' on both the base and
     * rider segments, and the insured client for the 'IS' and 'MD' transactions on the rider segments.
     * @throws Exception
     */
    private void composeBaseAndRiderSegmentVOs(SegmentVO segmentVO,
                                               ContractSetupVO contractSetupVO,
                                               ClientSetupVO clientSetupVO,
                                               EDITTrxVO editTrxVO) throws Exception
    {
        InvestmentAllocationOverrideVO[] investmentAllocationOverrideVO = contractSetupVO.getInvestmentAllocationOverrideVO();
        investmentAllocationOverrideVO = (InvestmentAllocationOverrideVO[]) Util.sortObjects(investmentAllocationOverrideVO, new String[] {"getToFromStatus"});
        contractSetupVO.removeAllInvestmentAllocationOverrideVO();
        contractSetupVO.setInvestmentAllocationOverrideVO(investmentAllocationOverrideVO);

        List investmentVOInclusionList = new ArrayList();
        investmentVOInclusionList.add(InvestmentVO.class);
        investmentVOInclusionList.add(InvestmentAllocationVO.class);
        investmentVOInclusionList.add(BucketVO.class);
        investmentVOInclusionList.add(BucketAllocationVO.class);
        investmentVOInclusionList.add(AnnualizedSubBucketVO.class);

        InvestmentVO[] investmentVO = new VOComposer().composeInvestmentVO(contractSetupVO.getSegmentFK(), investmentAllocationOverrideVO, investmentVOInclusionList, editTrxVO.getTransactionTypeCT());

        if (investmentVO != null)
        {
            super.sortBuckets(investmentVO, segmentVO, editTrxVO);
        }

        ClientVO clientVO = new event.dm.composer.ClientComposer().compose(clientSetupVO);

        SegmentVO[] riderSegments = segmentVO.getSegmentVO();
        segmentVO.removeAllSegmentVO();

        BaseSegmentVO baseSegmentVO = new BaseSegmentVO();

        if (investmentVO != null)
        {
            segmentVO.setInvestmentVO(investmentVO);
        }

        baseSegmentVO.setSegmentVO(segmentVO);
        baseSegmentVO.addClientVO(clientVO);

        if (riderSegments != null)
        {
            for (int i = 0; i < riderSegments.length; i++)
            {
                ClientVO riderInsuredClientVO = null;

                riderInsuredClientVO = this.buildInsuredClientVO(riderSegments[i]);
                if (riderInsuredClientVO != null)
                {
                    baseSegmentVO.addClientVO(riderInsuredClientVO);
                }
            }
        }

        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("BI"))
        {
            ClientVO  baseInsuredClientVO = this.buildInsuredClientVO(segmentVO);
            if (baseInsuredClientVO != null)
            {
                baseSegmentVO.addClientVO(baseInsuredClientVO);
            }
        }

        CommissionVO[] commissionVO = null;
        String commissionStatus = editTrxVO.getCommissionStatus();

        if (commissionStatus != null && commissionStatus.equalsIgnoreCase("P"))
        {
            AgentHierarchyVO[] agentHierarchyVO = segmentVO.getAgentHierarchyVO();

            if (agentHierarchyVO != null)
                commissionVO = getCommissionData(agentHierarchyVO);

            if (commissionVO != null)
            {
                baseSegmentVO.setCommissionVO(commissionVO);
            }
        }

        redoDocVO.setBaseSegmentVO(baseSegmentVO);

        if (riderSegments != null)
        {
            for (int i = 0; i < riderSegments.length; i++)
            {
                InvestmentVO[] riderInvestmentVO = new VOComposer().composeInvestmentVO(riderSegments[i].getSegmentFK(), investmentAllocationOverrideVO, investmentVOInclusionList, editTrxVO.getTransactionTypeCT());

                if (riderInvestmentVO != null)
                {
                    riderSegments[i].setInvestmentVO(riderInvestmentVO);
                }

                RiderSegmentVO riderSegmentVO = new RiderSegmentVO();
                riderSegmentVO.setSegmentVO(riderSegments[i]);

//                ClientVO riderInsuredClientVO = null;
//
//                if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("BI") ||
//                    editTrxVO.getTransactionTypeCT().equalsIgnoreCase("IS") ||
//                    editTrxVO.getTransactionTypeCT().equalsIgnoreCase("MD"))
//                {
//                    riderInsuredClientVO = this.buildInsuredClientVO(riderSegments[i]);
//                    if (riderInsuredClientVO != null)
//                    {
//                        riderSegmentVO.addClientVO(riderInsuredClientVO);
//                    }
//                }

                CommissionVO[] riderCommissionVO = null;
                commissionStatus = editTrxVO.getCommissionStatus();

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

                redoDocVO.addRiderSegmentVO(riderSegmentVO);
            }
        }
    }

    /**
     * Creates the InvestmentArrayVOs for the MF Transaction
     * @throws Exception
     */ 
    private void setInvestmentArrayVO_GeneralAccountArrayVO(SegmentVO segmentVO) throws Exception
    {
        EDITDate currMFEffDate = new EDITDate(editTrxVO.getEffectiveDate());
        EDITDate priorMFEffDate = getPriorMFEffDate(currMFEffDate);
        EDITDate edPolEffDate = new EDITDate(segmentVO.getEffectiveDate());

        if (new EDITDate(priorMFEffDate).before(edPolEffDate))
        {
            priorMFEffDate = edPolEffDate.subtractDays(1);
        }

        EDITDate edPriorMFDate = new EDITDate(priorMFEffDate);

        BusinessDay[] businessDays = BusinessDay.findBy_Range_Inclusive(priorMFEffDate, currMFEffDate);

        EDITTrxHistoryVO[] editTrxHistoryVOs = retrieveEDITTrxHistoryVOs(currMFEffDate,
                                                                         priorMFEffDate,
                                                                         segmentVO);

        InvestmentVO[] allInvestments = retrieveAllInvestmentsForSegment(segmentVO);

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
                redoDocVO.setGeneralAccountArrayVO(generalAccountArrayVOs);
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
                    EDITDate businessDate = businessDays[j].getBusinessDate();

                    if (j == 0 && !businessDate.equals(edPriorMFDate))
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
                                                                                                businessDate.getFormattedDate(),
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

                                    if ( (effectiveDate.after(priorUVEffDate) || effectiveDate.equals(priorUVEffDate))
                                        && effectiveDate.before(businessDate))
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
                                    if (edTrxEffDate.before(businessDate))
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

                                if (edTrxEffDate.equals(businessDate))
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
                                    investmentArrayVO.setUnitValueDate(businessDate.getFormattedDate());
                                    investmentArrayVO.setCumUnits(cumUnits.getBigDecimal());
                                    investmentArrayVO.setUnitValue(unitValue.getBigDecimal());

                                    invArrayVOMap.put(investmentArrayVO.getInvestmentFK() + "_" + investmentArrayVO.getUnitValueDate(), investmentArrayVO);

                                    priorUVEffDate = businessDate;
                                    priorCumUnits = cumUnits;
                                    priorChargeCodeFK = chargeCodeFK;
                                }
                            }
                        }
                    }


                    if (!exactMatchFound)
                    {
                        unitValuesVO = engineLookup.getUnitValuesByFund_ChargeCode_Date(filteredFundFK,
                                                                                        priorChargeCodeFK,
                                                                                        businessDate.getFormattedDate(),
                                                                                        "Forward");

                        unitValue = new EDITBigDecimal();

                        if (unitValuesVO != null && unitValuesVO.length > 0)
                        {
                            unitValue = new EDITBigDecimal(unitValuesVO[0].getUnitValue());
                        }

                        InvestmentArrayVO investmentArrayVO = new InvestmentArrayVO();
                        investmentArrayVO.setInvestmentFK(allInvestments[i].getInvestmentPK());
                        investmentArrayVO.setUnitValueDate(businessDate.getFormattedDate());
                        investmentArrayVO.setCumUnits(priorCumUnits.getBigDecimal());
                        investmentArrayVO.setUnitValue(unitValue.getBigDecimal());

                        invArrayVOMap.put(investmentArrayVO.getInvestmentFK() + "_" + investmentArrayVO.getUnitValueDate(), investmentArrayVO);
                        priorUVEffDate = businessDate;
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

            redoDocVO.setInvestmentArrayVO((InvestmentArrayVO[]) invArrayVOList.toArray(new InvestmentArrayVO[invArrayVOMap.size()]));
        }
    }

    /**
     * Calculates the effective date of the prior MF transaction
     * @param currMFEffDate
     * @return
     */ 
    private EDITDate getPriorMFEffDate(EDITDate currMFEffDate)
    {
        EDITDate tempMFEffDate = currMFEffDate.subtractMonths(1);

        EDITDate edPriorMFEffDate = tempMFEffDate.getEndOfMonthDate();

        BusinessCalendar businessCalendar = new BusinessCalendar();
        BusinessDay businessDay = businessCalendar.getBestBusinessDay(edPriorMFEffDate);
        edPriorMFEffDate = businessDay.getBusinessDate();

        return edPriorMFEffDate;
    }

    /**
     * Retrieve the EDITTrxHistory records to be used in creation of the InvestmentArrayVO
     * @param currMFEffDate
     * @param priorMFEffDate
     * @param segmentVO
     * @return
     * @throws Exception
     */
    private EDITTrxHistoryVO[] retrieveEDITTrxHistoryVOs(EDITDate currMFEffDate,
                                                         EDITDate priorMFEffDate,
                                                         SegmentVO segmentVO) throws Exception
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

    private InvestmentVO[] retrieveAllInvestmentsForSegment(SegmentVO segmentVO) throws Exception
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

    /**
     * Get the CommissionVO data for the given agent hierarchies.
     * @param agentHierarchyVO
     * @return
     * @throws Exception
     */
    public CommissionVO[] getCommissionData(AgentHierarchyVO[] agentHierarchyVO) throws Exception
    {
        List voInclusionList = new ArrayList();

        voInclusionList.add(AgentVO.class);
        voInclusionList.add(AgentContractVO.class);
        voInclusionList.add(AdditionalCompensationVO.class);
        voInclusionList.add(AgentLicenseVO.class);
        voInclusionList.add(CommissionProfileVO.class);
        voInclusionList.add(ClientRoleVO.class);
        voInclusionList.add(ClientRoleFinancialVO.class);

        List commissions = new ArrayList();

        for (int i = 0; i < agentHierarchyVO.length; i++)
        {
            AgentHierarchyAllocation agentHierarchyAllocation = AgentHierarchyAllocation.findActiveByAgentHierarchyFK_Date(
                    new Long(agentHierarchyVO[i].getAgentHierarchyPK()), new EDITDate(editTrxVO.getEffectiveDate()));

            if (agentHierarchyAllocation != null)   // if trx is back dated, may not have an active allocation for that effective date
            {
                EDITBigDecimal allocationPercent = agentHierarchyAllocation.getAllocationPercent();

                //  Only create commission data for agents whose allocationPercent is not zero
                if (allocationPercent.isGT(EDITBigDecimal.EDIT_DECIMAL_ZILCH_STR))
                {
                    CommissionVO commissionVO = new CommissionVO();

                    //order by hierarchy level
                    AgentSnapshotVO[] agentSnapshotVOs = contract.dm.dao.DAOFactory.getAgentSnapshotDAO().findSortedAgentSnapshotVOs(agentHierarchyVO[i].getAgentHierarchyPK());

                    EDITTrx editTrx = new EDITTrx(editTrxVO);
                    
                    AgentSnapshotDetailVO[] agentSnapshotDetailVO = new agent.dm.composer.VOComposer().composeCommissionVO(editTrx, agentSnapshotVOs, segmentVO.getSegmentPK(), voInclusionList);
                    commissionVO.setAllocationPercent( allocationPercent.getBigDecimal() );
                    commissionVO.setAgentSnapshotDetailVO(agentSnapshotDetailVO);
                    commissionVO.setCommissionPK(i + 1);
                    commissions.add(commissionVO);
                }
            }
        }

        return (CommissionVO[]) commissions.toArray(new CommissionVO[commissions.size()]);
    }

    /**
     * Associates the ContractClient's Billing info (if any).
     */
    private void associateBillingInfo() throws Exception
    {
        //  COMMENTED OUT METHOD BECAUSE BILLING AND BILLLAPSE ARE OBSOLETE BUT THE NEW WAY TO HANDLE BILLING HAS
        //  NOT BEEN DECIDED YET
//        String roleTypeCT = redoDocVO.getBaseSegmentVO().getClientVO(0).getRoleTypeCT();
//
//        if (roleTypeCT.equals("POR")) // This only applies to 'Payor' role types.
//        {
//            ContractClientVO contractClientVO = redoDocVO.getBaseSegmentVO().getClientVO(0).getContractClientVO(0);
//
//            BillingVO billingVO = getBillingInfoForPayor(contractClientVO);
//            if (billingVO != null)
//            {
//                redoDocVO.addBillingVO(billingVO);
//            }
//        }
//        else if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("IS"))
//        {
//            long segmentPK = redoDocVO.getBaseSegmentVO().getSegmentVO().getSegmentPK();
//
//            List voInclusionList = new ArrayList();
//            voInclusionList.add(ClientRoleVO.class);
//
//            ContractClientVO[] contractClientVOs = new VOComposer().composeContractClientVOBySegmentFK(segmentPK, voInclusionList);
//            for (int i = 0; i < contractClientVOs.length; i++)
//            {
//                ClientRoleVO clientRoleVO = (ClientRoleVO) contractClientVOs[i].getParentVO(ClientRoleVO.class);
//                if (clientRoleVO.getRoleTypeCT().equalsIgnoreCase("POR"))
//                {
//                    BillingVO billingVO = getBillingInfoForPayor(contractClientVOs[i]);
//                    if (billingVO != null)
//                    {
//                        redoDocVO.addBillingVO(billingVO);
//                    }
//
//                    break;
//                }
//            }
//        }
                    }

    /**
     * Retrieves BillLapse and Billing VOs for the given ContractClient (which at this point in time is the Payor Role)
     * @param contractClientVO
     * @return
     * @throws Exception
     */
    //  COMMENTED OUT METHOD BECAUSE BILLING AND BILLLAPSE ARE OBSOLETE BUT THE NEW WAY TO HANDLE BILLING HAS
    //  NOT BEEN DECIDED YET
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
     * RedoDoc VO needs to be prepared for PRASE. It is VO in memory only (no PKs, etc.)
     */
    private void prepareDocument()
    {
        EDITTrxVO editTrxVO = redoDocVO.getGroupSetupVO(0).getContractSetupVO(0).getClientSetupVO(0).getEDITTrxVO(0);
        // We do NOT want to change the EDITTrxHistory in any way if we are reapplying a "Forward" or "Backward" priced
        // transaction.
        if (!editTrxVO.getPendingStatus().equalsIgnoreCase("F") &&
            !editTrxVO.getPendingStatus().equalsIgnoreCase("B"))
        {
    //        // Prep EDITTrxHistoryVO
            EDITTrxHistoryVO editTrxHistoryVO = editTrxVO.getEDITTrxHistoryVO(0);

            EDITDate systemDate = new EDITDate();

            editTrxHistoryVO.setEDITTrxHistoryPK(0);
            editTrxHistoryVO.setCorrespondenceTypeCT(null);
            editTrxHistoryVO.setAccountingPendingStatus(null);
    //        editTrxHistoryVO.setProcessDate(systemDate);
    //        editTrxHistoryVO.setProcessTime(EDITDate.getCurrentTime());
            editTrxHistoryVO.setProcessDateTime(new EDITDateTime().getFormattedDateTime());
            editTrxHistoryVO.setCycleDate(systemDate.getFormattedDate());

            editTrxHistoryVO.setEDITTrxHistoryPK(0);
            editTrxHistoryVO.setCorrespondenceTypeCT(null);
            editTrxHistoryVO.setAccountingPendingStatus(null);
    //        editTrxHistoryVO.setProcessDate(systemDate);
    //        editTrxHistoryVO.setProcessTime(EDITDate.getCurrentTime());
            editTrxHistoryVO.setProcessDateTime(new EDITDateTime().getFormattedDateTime());
            editTrxHistoryVO.setCycleDate(systemDate.getFormattedDate());

            // Prep FinancialHistroyVO
            FinancialHistoryVO[] financialHistoryVO = editTrxHistoryVO.getFinancialHistoryVO();

            for (int i = 0; i < financialHistoryVO.length; i++)
            {
                financialHistoryVO[i].setFinancialHistoryPK(0);
                financialHistoryVO[i].setEDITTrxHistoryFK(0);
            }

            // Prep SegmentHistoryVO
            SegmentHistoryVO[] segmentHistoryVO = editTrxHistoryVO.getSegmentHistoryVO();

            for (int i = 0; i < segmentHistoryVO.length; i++)
            {
                segmentHistoryVO[i].setSegmentHistoryPK(0);
                segmentHistoryVO[i].setEDITTrxHistoryFK(0);
            }

            // Prep WithholdingHistoryVO
            WithholdingHistoryVO[] withholdingHistoryVO = editTrxHistoryVO.getWithholdingHistoryVO();

            for (int i = 0; i < withholdingHistoryVO.length; i++)
            {
                withholdingHistoryVO[i].setEDITTrxHistoryFK(0);
                withholdingHistoryVO[i].setWithholdingHistoryPK(0);
            }

            // Prep ChargeHistoryVO
            ChargeHistoryVO[] chargeHistoryVO = editTrxHistoryVO.getChargeHistoryVO();

            for (int i = 0; i < chargeHistoryVO.length; i++)
            {
                chargeHistoryVO[i].setChargeHistoryPK(0);
                chargeHistoryVO[i].setEDITTrxHistoryFK(0);
            }

            if (!editTrxVO.getPendingStatus().equalsIgnoreCase("F") &&
                !editTrxVO.getPendingStatus().equalsIgnoreCase("B"))
            {
                // Prep BucketHistoryVO
                BucketHistoryVO[] bucketHistoryVO = editTrxHistoryVO.getBucketHistoryVO();

                for (int i = 0; i < bucketHistoryVO.length; i++)
                {
                    bucketHistoryVO[i].setBucketHistoryPK(0);
                    bucketHistoryVO[i].setPreviousValue(new BigDecimal(0));

                    /* bucketHistoryVO[i].setCumDollars(0);
                    bucketHistoryVO[i].setCumUnits(0);
                    bucketHistoryVO[i].setGainLoss(0); */
                    // commented above line(s) for double to BigDecimal conversion
                    // sprasad 9/27/2004
                    bucketHistoryVO[i].setCumDollars( new EDITBigDecimal().getBigDecimal() );
                    bucketHistoryVO[i].setCumUnits( new EDITBigDecimal().getBigDecimal() );
        //            bucketHistoryVO[i].setGainLoss( new EDITBigDecimal().getBigDecimal() );

                    bucketHistoryVO[i].setEDITTrxHistoryFK(0);
                    if (bucketHistoryVO[i].getToFromStatus().equals("T"))
                    {
                        bucketHistoryVO[i].setToFromStatus("F");
                    }
                    else if (bucketHistoryVO[i].getToFromStatus().equals("F"))
                    {
                        bucketHistoryVO[i].setToFromStatus("T");
                    }
                }
            }

            CommissionHistoryVO[] commissionHistoryVOs = editTrxHistoryVO.getCommissionHistoryVO();

            boolean includeRedoTransactionsInCommssionStatement = includeRedoTransactionsInCommissionStatement();

            List commissionHistory = new ArrayList();;
            for (int i = 0; i < commissionHistoryVOs.length; i++)
            {
                String undoRedoStatus = commissionHistoryVOs[i].getUndoRedoStatus();

                if (undoRedoStatus == null || !undoRedoStatus.equalsIgnoreCase("undo"))
                {
                    commissionHistoryVOs[i].setCommissionHistoryPK(0);
                    commissionHistoryVOs[i].setEDITTrxHistoryFK(0);
                    commissionHistoryVOs[i].setUpdateStatus("U");
                    commissionHistoryVOs[i].setAccountingPendingStatus("Y");
    //                commissionHistoryVOs[i].setUpdateDateTime("0000/00/00 00:00:00:000");
                    commissionHistoryVOs[i].setUndoRedoStatus("redo");
                    commissionHistory.add(commissionHistoryVOs[i]);

                    if (includeRedoTransactionsInCommssionStatement)
                    {
                        commissionHistoryVOs[i].setStatementInd("Y");
                }
                    else
                    {
                        commissionHistoryVOs[i].setStatementInd("N");
            }
                }
            }

            if (commissionHistory.size() > 0)
            {
                editTrxHistoryVO.removeAllCommissionHistoryVO();
                editTrxHistoryVO.setCommissionHistoryVO((CommissionHistoryVO[]) commissionHistory.toArray(new CommissionHistoryVO[commissionHistory.size()]));
            }

            ReinsuranceHistoryVO[] reinsuranceHistoryVOs = editTrxHistoryVO.getReinsuranceHistoryVO();

            List reinsuranceHistory = new ArrayList();;
            for (int i = 0; i < reinsuranceHistoryVOs.length; i++)
            {
                String undoRedoStatus = reinsuranceHistoryVOs[i].getUndoRedoStatus();

                if (undoRedoStatus == null || !undoRedoStatus.equalsIgnoreCase("undo"))
                {
                    reinsuranceHistoryVOs[i].setReinsuranceHistoryPK(0);
                    reinsuranceHistoryVOs[i].setEDITTrxHistoryFK(0);
                    reinsuranceHistoryVOs[i].setUpdateStatus("U");
                    reinsuranceHistoryVOs[i].setAccountingPendingStatus("Y");
                    reinsuranceHistoryVOs[i].setUndoRedoStatus("redo");
                    reinsuranceHistory.add(reinsuranceHistoryVOs[i]);
                }
            }

            if (reinsuranceHistory.size() > 0)
            {
                editTrxHistoryVO.removeAllReinsuranceHistoryVO();
                editTrxHistoryVO.setReinsuranceHistoryVO((ReinsuranceHistoryVO[]) reinsuranceHistory.toArray(new ReinsuranceHistoryVO[reinsuranceHistory.size()]));
            }
        }
//        We are no longer including InvestmentHistoryVO's in the redoDocument (8/10/2005 - DL)
//        InvestmentHistoryVO[] investmentHistoryVOs = editTrxHistoryVO.getInvestmentHistoryVO();
//
//        investmentHistoryVOs = (InvestmentHistoryVO[]) Util.sortObjects(investmentHistoryVOs, new String[] {"getToFromStatus"});
//
//        for (int i = 0; i < investmentHistoryVOs.length; i++)
//        {
//            investmentHistoryVOs[i].setInvestmentHistoryPK(0);
//            investmentHistoryVOs[i].setEDITTrxHistoryFK(0);
//        }
    }

    /**
     * Returns the composed RedoDocVO.
     * @return
     */
    public VOObject getDocumentAsVO()
    {
        return redoDocVO;
    }

   public void checkForTransactionAccumGeneration(SegmentVO segmentVO)
    {
        String trxType = editTrxVO.getTransactionTypeCT();

        HashMap trxAccums = new HashMap();

        // Set these script fields in all cases just like NaturalDoc does.
        // used to limit it to PY, CC, ...
   
        trxAccums = super.getTransactionAccumFields(trxType, editTrxVO, segmentVO);
        setAccumsInDocument(trxAccums);
    }

   /**
     * For the Accumulations performed now update the Redo Doc.  If the fields were null, zeros were
     * set into its value.
     * @param trxAccums
     */
    private void setAccumsInDocument(HashMap trxAccums)
    {
        redoDocVO.setPremiumToDate((BigDecimal)trxAccums.get("PremiumToDate"));

        redoDocVO.setPremiumYearToDate((BigDecimal)trxAccums.get("PremiumYearToDate"));

        redoDocVO.setPremiumCalYearToDate((BigDecimal)trxAccums.get("PremiumCalYearToDate"));

        redoDocVO.setNetWithdrawalsToDate((BigDecimal)trxAccums.get("WithDToDate"));

        redoDocVO.setNetWithdrawalsYearToDate((BigDecimal)trxAccums.get("NetWithDYearToDate"));

        redoDocVO.setCumInitialPremium((BigDecimal)trxAccums.get("CumInitialPrem"));

        redoDocVO.setCum1035Premium((BigDecimal)trxAccums.get("Cum1035Prem"));

        redoDocVO.setPremiumSinceLast7PayDate((BigDecimal)trxAccums.get("PremSinceLast7Pay"));

        redoDocVO.setWithdrawalsSinceLast7PayDate((BigDecimal)trxAccums.get("WithDSinceLast7Pay"));

        redoDocVO.setNumberTransfersPolYearToDate(((Integer)trxAccums.get("NumberWithDToDate")).intValue());

        redoDocVO.setNumberWithdrawalsPolYearToDate(((Integer)trxAccums.get("NumberTransfersToDate")).intValue());
    }

    /**
     * Populates OwnersPremiumTaxState in RedoDocVO.
     * @param segmentVO
     */
    private void setOwnersPremiumTaxState(SegmentVO segmentVO)
    {
        Segment segment = new Segment(segmentVO);

        ClientDetail ownerClientDetail = segment.getOwner();

        ClientAddress ownerPrimaryAddress = ownerClientDetail.getPrimaryAddress();

        redoDocVO.setOwnersPremiumTaxState(ownerPrimaryAddress.getStateCT());
    }

    /**
     * Populates Insured fields.
     */
    private void setInsuredFields(SegmentVO segmentVO)
    {
        Segment segment = new Segment(segmentVO);

        // only life and traditional policies will have insured role.
        if (Segment.SEGMENTNAMECT_LIFE.equalsIgnoreCase(segment.getSegmentNameCT()) ||
            Segment.SEGMENTNAMECT_TRADITIONAL.equalsIgnoreCase(segment.getSegmentNameCT()))
        {
            ClientDetail insured = segment.getInsured();

            if (insured != null)
            {
                redoDocVO.setInsuredGender(insured.getGenderCT());
                redoDocVO.setInsuredDateOfBirth(insured.getBirthDate() == null ? null : insured.getBirthDate().getFormattedDate());

                ClientAddress insuredAddress = insured.getPrimaryAddress();

                if (insuredAddress != null)
                {
                    redoDocVO.setInsuredResidenceState(insuredAddress.getStateCT());
                }
            }

            ContractClient insuredContractClient = segment.getInsuredContractClient();

            if (insuredContractClient != null)
            {
                redoDocVO.setInsuredIssueAge(insuredContractClient.getIssueAge());
                redoDocVO.setInsuredClass(insuredContractClient.getClassCT());
                redoDocVO.setInsuredUnderwritingClass(insuredContractClient.getUnderwritingClassCT());
                redoDocVO.setInsuredRatedGender(insuredContractClient.getRatedGenderCT());
            }
        }
    }

    private void setDeathInformatonFields(SegmentVO segmentVO)
    {
        DeathInformationVO deathInformationVO = super.getDeathInformationFromClient(segmentVO.getSegmentPK(), segmentVO.getSegmentNameCT());
        redoDocVO.addDeathInformationVO(deathInformationVO);
    }

    /**
     * Retrieve the date of birth of the owner (if it is a "natural" client).  If the owner is a
     * Non-natural entity (not Male or Female), the retrieve the Annuitant or Insured (depending on product type)
     * date of birth.
     * @param segmentVO  The segmentVO for the transaction being processed through the redoDocVO.
     */
    private void getTaxDateOfBirth(SegmentVO segmentVO)
    {
        Segment segment = new Segment(segmentVO);

        ClientDetail ownerClientDetail = segment.getOwner();

        if (ownerClientDetail.getGenderCT() == null ||
            ownerClientDetail.getGenderCT().equalsIgnoreCase("NonNatural") ||
            ownerClientDetail.getGenderCT().equalsIgnoreCase("NotApplicable"))
        {
            ClientDetail annuitantClientDetail = segment.getAnnuitant();

            if (annuitantClientDetail != null)
            {
                redoDocVO.setTaxDateOfBirth(annuitantClientDetail.getBirthDate().getFormattedDate());
            }
            else
            {
                ClientDetail insuredClientDetail = segment.getInsured();

                if (insuredClientDetail != null)
                {
                    redoDocVO.setTaxDateOfBirth(insuredClientDetail.getBirthDate().getFormattedDate());
                }
            }
        }
        else
        {
            redoDocVO.setTaxDateOfBirth(ownerClientDetail.getBirthDate().getFormattedDate());
        }
    }

    protected void composeOverdueChargesRemaining(SegmentVO segmentVO) throws Exception
    {
        OverdueChargeVO[] overdueChargeVOs = new OverdueChargeDAO().findBySegmentPK_AND_TransactionType(segmentVO.getSegmentPK(), new String[] {"MD", "ML"}, editTrxVO.getEffectiveDate());

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

                    redoDocVO.addOverdueChargeRemainingVO(overdueChargeRemainingVO);
                }
            }
        }
    }

    /**
     * Returns true if the IncludeUndoTransactionsInCommissionStatementIndicator is set to 'Y' in configuration file.
     * @return
     */
    private boolean includeRedoTransactionsInCommissionStatement()
    {
        boolean includeUndoTransactionsInCommissionStatement = false;

        String includeUndoTransactionsInCommissionStatementIndicator = ServicesConfig.getIncludeUndoTransactionsInCommissionStatementsIndicator();

        if (includeUndoTransactionsInCommissionStatementIndicator != null &&
                includeUndoTransactionsInCommissionStatementIndicator.equals("Y"))
        {
            includeUndoTransactionsInCommissionStatement = true;
        }

        return includeUndoTransactionsInCommissionStatement;
    }

    /**
     * The Face Decrease trx needs premium information for Tamra Retest
     */
    private void setTamraRetestVO(SegmentVO segmentVO)
    {
        TamraRetestVO[] tamraRetestVOs = super.getTamraRetestVOs(segmentVO, editTrxVO);

        if (tamraRetestVOs != null)
        {
            redoDocVO.setTamraRetestVO(tamraRetestVOs);
        }
    }
}
