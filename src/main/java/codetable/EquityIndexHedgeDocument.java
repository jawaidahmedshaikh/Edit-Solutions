/*
 * User: dlataill
 * Date: Feb 5, 2004
 * Time: 9:02:09 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC. All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential. Any use is
 * subject to the license agreement.
 */
package codetable;

import client.*;

import contract.*;

import edit.common.*;

import edit.common.vo.*;

import edit.services.db.hibernate.*;

import engine.*;

import event.*;

import fission.utility.*;

import org.dom4j.*;

import org.dom4j.tree.*;

import role.*;

import java.util.*;


public class EquityIndexHedgeDocument
{
    private Segment segment;
    private ProductStructure productStructure;
    private Document document;
    private long highestSequenceNumber;
    private int equityIndexHedgeDetailVOCount;

    public EquityIndexHedgeDocument(Segment segment, ProductStructure productStructure)
    {
        super();

        this.segment = segment;
        this.productStructure = productStructure;
    }

    /**
     * Build NaturalDocVO for Equity Index Hedge Processor
     */
    public void buildDocument(long equityIndexHedgeCounter, EDITDate runDate, String createSubBucketInd)
    {
        Map investPriorAnniversaryAcctValueHT = new HashMap();
        Map investWithdrawalsSincePriorAnniversary = new HashMap();
        EDITTrxHistory[] polYearEndEDITTrxHistories = null;
        EDITBigDecimal surrenderValue = null;
        EDITTrxHistory[] withdrawalEDITTrxHistories = null;
        EDITTrxHistory[] calYearEndEDITTrxHistories = null;
        EDITBigDecimal priorYearEndAccountValue = null;
        EDITBigDecimal priorYearEndCashSurrenderValue = null;
        EDITBigDecimal priorYearEndMinAcctValue = null;
        NaturalDocVO naturalDocVO = new NaturalDocVO();
        String ownerSex = null;
        int ownerIssueAge = 0;
        String ownerResidentState = null;
        String annuitantSex = null;
        int annuitantIssueAge = 0;
        String jointAnnSex = null;
        int jointAnnIssueAge = 0;

        try
        {
            document = new DefaultDocument();

            Element baseSegmentElement = Util.buildElement("BaseSegmentVO", null);

            Element segmentElement = composeSegmentElement(segment);

            baseSegmentElement.add(segmentElement);

            Element[] riderElements = getRiderSegments();

            EDITDate lastAnnivDate = segment.getLastAnniversaryDate();

            String[] historyTrx = new String[] { "PE" };

            if (!lastAnnivDate.equals(segment.getEffectiveDate()))
            {
                //get PolYearEnd transaction w/ eff date = lastAnnivDate
                polYearEndEDITTrxHistories = composeEDITTrxHistoryByDatesTrxType(segment.getSegmentPK(), lastAnnivDate,
                        lastAnnivDate, historyTrx);

                if (polYearEndEDITTrxHistories.length > 0)
                {
                    Set bucketHistories = polYearEndEDITTrxHistories[0].getBucketHistories();

                    for (Iterator iterator = bucketHistories.iterator(); iterator.hasNext();)
                    {
                        BucketHistory currentBucketHistory = (BucketHistory) iterator.next();

                        Bucket bucket = currentBucketHistory.getBucket();

                        Long investmentFK = bucket.getInvestmentFK();

                        addToPriorAnniversaryAccountValue(investPriorAnniversaryAcctValueHT, investmentFK,
                            currentBucketHistory.getCumDollars());
                    }

                    FinancialHistory financialHistory = polYearEndEDITTrxHistories[0].getFinancialHistory();

                    surrenderValue = financialHistory.getSurrenderValue();
                }
            }

            historyTrx = new String[] { "CY" };

            //get last CalYearEnd transaction
            calYearEndEDITTrxHistories = composeEDITTrxHistoryByDateLTE_And_TrxType(segment.getSegmentPK(), runDate,
                    historyTrx);

            boolean cyTrxFound = false;

            if (calYearEndEDITTrxHistories.length > 0)
            {
                FinancialHistory financialHistory = calYearEndEDITTrxHistories[calYearEndEDITTrxHistories.length - 1].getFinancialHistory();

                priorYearEndAccountValue = financialHistory.getAccumulatedValue();

                priorYearEndCashSurrenderValue = financialHistory.getSurrenderValue();

                priorYearEndMinAcctValue = financialHistory.getGuarAccumulatedValue();

                cyTrxFound = true;
            }

            historyTrx = new String[] { "WI", "SW" };

            //get withdrawals from lastAnnivDate up to and including the current date
            withdrawalEDITTrxHistories = composeEDITTrxHistoryByDatesTrxType(segment.getSegmentPK(), lastAnnivDate,
                    runDate, historyTrx);

            if (withdrawalEDITTrxHistories.length > 0)
            {
                for (int w = 0; w < withdrawalEDITTrxHistories.length; w++)
                {
                    Set bucketHistories = withdrawalEDITTrxHistories[w].getBucketHistories();

                    for (Iterator iterator = bucketHistories.iterator(); iterator.hasNext();)
                    {
                        BucketHistory currentBucketHistory = (BucketHistory) iterator.next();

                        Bucket currentBucket = currentBucketHistory.getBucket();

                        Long investmentFK = currentBucket.getInvestmentFK();

                        addToInvestmentWithdrawalSincePriorAnniversary(investWithdrawalsSincePriorAnniversary,
                            investmentFK, currentBucketHistory.getDollars());
                    }
                }
            }

            Set contractClients = segment.getContractClients();

            for (Iterator iterator = contractClients.iterator(); iterator.hasNext();)
            {
                ContractClient currentContractClient = (ContractClient) iterator.next();
                Element contractClientElement = SessionHelper.mapToElement(currentContractClient, SessionHelper.EDITSOLUTIONS, false, false);

                ClientRole currentClientRole = currentContractClient.getClientRole();

                //check for terminated owner don't use it if found
                boolean terminationDateIsGTERunDate = false;

                if (currentClientRole.getRoleTypeCT().equalsIgnoreCase("OWN") ||
                    currentClientRole.getRoleTypeCT().equalsIgnoreCase("ANN") ||
                    currentClientRole.getRoleTypeCT().equalsIgnoreCase("SAN"))
                {
                    terminationDateIsGTERunDate = currentContractClient.getTerminationDate().after(runDate) || currentContractClient.getTerminationDate().equals(runDate);
                }

                if (terminationDateIsGTERunDate)
                {
                    Element clientElement = PRASEDocHelper.buildClient(currentClientRole, false).getElement();
                    clientElement.add(contractClientElement);

                    baseSegmentElement.add(clientElement);

                    String currentRoleTypeCT = currentClientRole.getRoleTypeCT();

                    ClientDetail currentClientDetail = currentClientRole.getClientDetail();

                    ClientAddress currentClientPrimaryAddress = currentClientDetail.getPrimaryAddress();

                    if (currentRoleTypeCT.equalsIgnoreCase("OWN"))
                    {
                        ownerSex = currentClientDetail.getGenderCT();
                        ownerIssueAge = currentContractClient.getIssueAge();
                        if (currentClientPrimaryAddress != null)
                        {
                            ownerResidentState = currentClientPrimaryAddress.getStateCT();
                        }
                    }
                    else if (currentRoleTypeCT.equalsIgnoreCase("ANN"))
                    {
                        annuitantSex = currentClientDetail.getGenderCT();
                        annuitantIssueAge = currentContractClient.getIssueAge();
                    }
                    else if (currentRoleTypeCT.equalsIgnoreCase("SAN"))
                    {
                        jointAnnSex = currentClientDetail.getGenderCT();
                        jointAnnIssueAge = currentContractClient.getIssueAge();
                    }
                }
            }

            Set investments = segment.getInvestments();

            for (Iterator iterator = investments.iterator(); iterator.hasNext();)
            {
                Investment currentInvestment = (Investment) iterator.next();

                Long investmentPK = currentInvestment.getInvestmentPK();

                Set buckets = currentInvestment.getBuckets();
                Iterator bucketIt = buckets.iterator();
                while (bucketIt.hasNext())
                {
                    Bucket currentBucket = (Bucket) bucketIt.next();

                    equityIndexHedgeCounter += 1;

                    EquityIndexHedgeDetailVO equityIndexHedgeDetailVO = new EquityIndexHedgeDetailVO();

                    equityIndexHedgeDetailVO.setEquityIndexHedgeDetailPK(equityIndexHedgeCounter);

                    equityIndexHedgeDetailVO.setCreateSubBucketInd(createSubBucketInd);

                    if (investPriorAnniversaryAcctValueHT.containsKey(investmentPK))
                    {
                        equityIndexHedgeDetailVO.setPriorAnnivAccountValue(((EDITBigDecimal) investPriorAnniversaryAcctValueHT.get(
                                investmentPK)).getBigDecimal());
                    }
                    else
                    {
                        equityIndexHedgeDetailVO.setPriorAnnivAccountValue(new EDITBigDecimal().getBigDecimal());
                    }

                    if (investWithdrawalsSincePriorAnniversary.containsKey(investmentPK))
                    {
                        equityIndexHedgeDetailVO.setWithdrawalsSincePriorAnniv(((EDITBigDecimal) investWithdrawalsSincePriorAnniversary.get(
                                investmentPK)).getBigDecimal());
                    }
                    else
                    {
                        equityIndexHedgeDetailVO.setWithdrawalsSincePriorAnniv(new EDITBigDecimal().getBigDecimal());
                    }

                FilteredFund filteredFund = FilteredFund.findByPK(currentInvestment.getFilteredFundFK());

                    equityIndexHedgeDetailVO.setFundNumber(filteredFund.getFundNumber().trim());
                    equityIndexHedgeDetailVO.setBucketFK(currentBucket.getBucketPK().longValue());

                    equityIndexHedgeDetailVO.setEffectiveDate(runDate.getFormattedDate());
                    equityIndexHedgeDetailVO.setOwnerIssueAge(ownerIssueAge);
                    equityIndexHedgeDetailVO.setOwnerSex(ownerSex);
                    equityIndexHedgeDetailVO.setAnnuitantIssueAge(annuitantIssueAge);
                    equityIndexHedgeDetailVO.setAnnuitantSex(annuitantSex);
                    equityIndexHedgeDetailVO.setJointAnnuitantAge(jointAnnIssueAge);
                    equityIndexHedgeDetailVO.setJointAnnuitantSex(jointAnnSex);
                    equityIndexHedgeDetailVO.setResidentStateOfOwner(ownerResidentState);

                    if (surrenderValue != null)
                    {
                        equityIndexHedgeDetailVO.setPriorYearSurrenderValue(surrenderValue.getBigDecimal());
                    }

                    equityIndexHedgeDetailVO.setCompanyName(productStructure.getCompanyName());
                    equityIndexHedgeDetailVO.setMarketingPackageName(productStructure.getMarketingPackageName());
                    equityIndexHedgeDetailVO.setGroupProductName(productStructure.getGroupProductName());
                    equityIndexHedgeDetailVO.setAreaName(productStructure.getAreaName());
                    equityIndexHedgeDetailVO.setBusinessContractName(productStructure.getBusinessContractName());
					equityIndexHedgeDetailVO.setSegmentStatus(segment.getSegmentStatusCT());

                    //these values are populated from the CY trx
                    if (priorYearEndAccountValue != null)
                    {
                        equityIndexHedgeDetailVO.setPriorYearEndAcctValue(priorYearEndAccountValue.getBigDecimal());
                    }

                    if (priorYearEndCashSurrenderValue != null)
                    {
                        equityIndexHedgeDetailVO.setPriorYearEndCashSurrenderValue(priorYearEndCashSurrenderValue.getBigDecimal());
                    }

                    if (priorYearEndMinAcctValue != null)
                    {
                        equityIndexHedgeDetailVO.setPriorYearEndMinAcctValue(priorYearEndMinAcctValue.getBigDecimal());
                    }

                    if (createSubBucketInd.equalsIgnoreCase("Y"))
                    {
                        if (filteredFund.getAnnualSubBucketCT().equalsIgnoreCase("CYE") && cyTrxFound)
                        {
                            loadSubBucketInformation(currentInvestment, calYearEndEDITTrxHistories, equityIndexHedgeDetailVO);
                        }
                    }

                    naturalDocVO.addEquityIndexHedgeDetailVO(equityIndexHedgeDetailVO);
                }
            }

            Element groupSetupElement = Util.buildElement("GroupSetupVO", null);
            Element contractSetupElement = Util.buildElement("ContractSetupVO", null);
            Element clientSetupElement = Util.buildElement("ClientSetupVO", null);
            Element editTrxElement = Util.buildElement("EDITTrxVO", null);

            Util.addValue(editTrxElement, "EffectiveDate", runDate.getFormattedDate());
            clientSetupElement.add(editTrxElement);
            contractSetupElement.add(clientSetupElement);
            groupSetupElement.add(contractSetupElement);

            Element naturalDocElement = VOObject.map(naturalDocVO);

            naturalDocElement.add(groupSetupElement);
            naturalDocElement.add(baseSegmentElement);
            for (int i = 0; i < riderElements.length; i++)
            {
                Element riderSegmentElement = Util.buildElement("RiderSegmentVO", null);

                riderSegmentElement.add(riderElements[i]);

                naturalDocElement.add(riderSegmentElement);
            }

            determineHighestEquityIndexHedgeCounter(naturalDocVO);

            equityIndexHedgeDetailVOCount = naturalDocVO.getEquityIndexHedgeDetailVOCount();

            document.setRootElement(naturalDocElement);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
    }

    /**
     * Maintains a summary of Withdrawals Since Prior Anniversary Account Values
     * @param investPriorAnniversaryAcctValueHT
     * @param investmentFK
     * @param cumDollars
     */
    private void addToInvestmentWithdrawalSincePriorAnniversary(Map investWithdrawalsSincePriorAnniversary,
                                                                Long investmentFK, EDITBigDecimal cumDollars)
    {
        EDITBigDecimal cumulativeValue;

        if (investWithdrawalsSincePriorAnniversary.containsKey(investmentFK))
        {
            cumulativeValue = (EDITBigDecimal) investWithdrawalsSincePriorAnniversary.get(investmentFK);

            cumulativeValue = cumulativeValue.addEditBigDecimal(cumDollars);

            investWithdrawalsSincePriorAnniversary.put(investmentFK, cumulativeValue);
        }
        else
        {
            investWithdrawalsSincePriorAnniversary.put(investmentFK, cumDollars);
        }
    }

    /**
     * Maintains a summary of Prior Anniversary Account Values
     * @param investPriorAnniversaryAcctValueHT
     * @param investmentFK
     * @param cumDollars
     */
    private void addToPriorAnniversaryAccountValue(Map investPriorAnniversaryAcctValueHT, Long investmentFK,
                                                   EDITBigDecimal cumDollars)
    {
        EDITBigDecimal cumulativeValue;

        if (investPriorAnniversaryAcctValueHT.containsKey(investmentFK))
        {
            cumulativeValue = (EDITBigDecimal) investPriorAnniversaryAcctValueHT.get(investmentFK);

            cumulativeValue = cumulativeValue.addEditBigDecimal(cumDollars);

            investPriorAnniversaryAcctValueHT.put(investmentFK, cumulativeValue);
        }
        else
        {
            investPriorAnniversaryAcctValueHT.put(investmentFK, cumDollars);
        }
    }

    public void loadSubBucketInformation(Investment investment, EDITTrxHistory[] calYearEndEDITTrxHistories,
                                         EquityIndexHedgeDetailVO equityIndexHedgeDetailVO)
    {
        Long cyEditTrxFK = null;

        for (int i = 0; i < calYearEndEDITTrxHistories.length; i++)
        {
            cyEditTrxFK = calYearEndEDITTrxHistories[i].getEDITTrxFK();

            Set buckets = investment.getBuckets();

            Set annualizedSubBuckets = null;

            SubBucketDetailVO subBucketDetailVO = null;

            for (Iterator iterator = buckets.iterator(); iterator.hasNext();)
            {
                Bucket currentBucket = (Bucket) iterator.next();

                annualizedSubBuckets = currentBucket.getAnnualizedSubBuckets();

                for (Iterator iterator1 = annualizedSubBuckets.iterator(); iterator1.hasNext();)
                {
                    AnnualizedSubBucket currentAnnualizedSubBucket = (AnnualizedSubBucket) iterator1.next();

                    if (currentAnnualizedSubBucket.getEDITTrxFK().equals(cyEditTrxFK))
                    {
                        subBucketDetailVO = new SubBucketDetailVO();
                        subBucketDetailVO.setSBEffDate(currentAnnualizedSubBucket.getSBEffDate().getFormattedDate());
                        subBucketDetailVO.setSBCurrentRate(currentAnnualizedSubBucket.getSBCurrentRate().getBigDecimal());
                        subBucketDetailVO.setSBCurrentEndDate(currentAnnualizedSubBucket.getSBCurrentEndDate().getFormattedDate());
                        subBucketDetailVO.setSBBaseRate(currentAnnualizedSubBucket.getSBBaseRate().getBigDecimal());
                        subBucketDetailVO.setSBBaseEndDate(currentAnnualizedSubBucket.getSBBaseEndDate().getFormattedDate());
                        subBucketDetailVO.setSBGuarMinRate1(currentAnnualizedSubBucket.getSBGuarMinRate1()
                                                                                      .getBigDecimal());
                        subBucketDetailVO.setSBGuarMinEndDate1(currentAnnualizedSubBucket.getSBGuarMinEndDate1()
                                                                                         .getFormattedDate());
                        subBucketDetailVO.setSBGuarMinRate2(currentAnnualizedSubBucket.getSBGuarMinRate2()
                                                                                      .getBigDecimal());
                        subBucketDetailVO.setSBGuarMinEndDate2(currentAnnualizedSubBucket.getSBGuarMinEndDate2()
                                                                                         .getFormattedDate());
                        subBucketDetailVO.setSBNumberBuckets(currentAnnualizedSubBucket.getSBNumberBuckets());
                        subBucketDetailVO.setSBFundValue(currentAnnualizedSubBucket.getSBFundValue().getBigDecimal());
                        subBucketDetailVO.setEquityIndexHedgeDetailFK(equityIndexHedgeDetailVO.getEquityIndexHedgeDetailPK());
                        equityIndexHedgeDetailVO.addSubBucketDetailVO(subBucketDetailVO);
                    }
                }
            }
        }
    }

    /**
     * Retrieve and return the resident state from the primary address of the Owner client
     * @param clientAddressVOs
     * @return
     */
    private String getOwnerResidentState(ClientAddressVO[] clientAddressVOs)
    {
        String residentState = null;

        for (int i = 0; i < clientAddressVOs.length; i++)
        {
            if (clientAddressVOs[i].getAddressTypeCT().equalsIgnoreCase("PrimaryAddress"))
            {
                residentState = clientAddressVOs[i].getStateCT();

                break;
            }
        }

        return residentState;
    }

    /**
     * Finds matching EDITrxHistory composed with:
     * EDITrxHistory
     * EDITTrx
     * BucketHistory
     * Bucket
     * FinancialHistory
     * EDITTrx.Status is hard-coded as ('N', 'A')
     * EDITTrx.PendingStatus is hard-coded as 'H';
     * @param segmentPK
     * @param fromDate
     * @param toDate
     * @param historyTrx
     * @return
     */
    private EDITTrxHistory[] composeEDITTrxHistoryByDatesTrxType(Long segmentPK, EDITDate fromDate, EDITDate toDate,
                                                                 String[] historyTrx)
    {
        String hql = " select distinct editTrxHistory from EDITTrxHistory editTrxHistory" +
            " join fetch editTrxHistory.EDITTrx editTrx" + " join fetch editTrx.ClientSetup clientSetup" +
            " join fetch clientSetup.ContractSetup contractSetup" +
            " join fetch editTrxHistory.BucketHistories bucketHistory" +
            " join fetch editTrxHistory.FinancialHistories" + " join fetch bucketHistory.Bucket" +
            " where contractSetup.SegmentFK = :segmentFK" + " and editTrx.Status in ('N', 'A')" +
            " and editTrx.EffectiveDate >= :fromDate" + " and editTrx.EffectiveDate <= :toDate" +
            " and editTrx.PendingStatus = 'H'" + " and editTrx.TransactionTypeCT in (";

        for (int i = 0; i < historyTrx.length; i++)
        {
            if (i < (historyTrx.length - 1))
            {
                hql += (":transactionTypeCT" + i + ", ");
            }
            else
            {
                hql += (":transactionTypeCT" + i);
            }
        }

        hql += ")";

        Map params = new HashMap();

        params.put("segmentFK", segmentPK);

        params.put("fromDate", fromDate);

        params.put("toDate", toDate);

        for (int i = 0; i < historyTrx.length; i++)
        {
            String currentTranscationTypeCT = historyTrx[i];

            params.put("transactionTypeCT" + i, currentTranscationTypeCT);
        }

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        List uniqueResults = new ArrayList(new HashSet(results));

        return (EDITTrxHistory[]) uniqueResults.toArray(new EDITTrxHistory[uniqueResults.size()]);
    }

    /**
     * Finds matching EDITrxHistory composed with:
     * EDITrxHistory
     * EDITTrx
     * BucketHistory
     * Bucket
     * FinancialHistory
     * EDITTrx.Status is hard-coded as ('N', 'A')
     * EDITTrx.PendingStatus is hard-coded as 'H';
     * @param segmentPK
     * @param fromDate
     * @param toDate
     * @param historyTrx
     * @return
     */
    private EDITTrxHistory[] composeEDITTrxHistoryByDateLTE_And_TrxType(Long segmentPK, EDITDate runDate,
                                                                        String[] historyTrx)
    {
        EDITDateTime runDateTime = new EDITDateTime(runDate, EDITDateTime.DEFAULT_MAX_TIME  + ":999");

        String hql = " select editTrxHistory from EDITTrxHistory editTrxHistory" +
            " join fetch editTrxHistory.EDITTrx editTrx" + " join fetch editTrx.ClientSetup clientSetup" +
            " join fetch clientSetup.ContractSetup contractSetup" +
            " join fetch editTrxHistory.BucketHistories bucketHistory" +
            " join fetch editTrxHistory.FinancialHistories" + " join fetch bucketHistory.Bucket" +
            " where contractSetup.SegmentFK = :segmentFK" + " and editTrx.Status in ('N', 'A')" +
            " and editTrxHistory.ProcessDateTime < :runDate" + " and editTrx.PendingStatus = 'H'" +
            " and editTrx.TransactionTypeCT in (";

        for (int i = 0; i < historyTrx.length; i++)
        {
            if (i < (historyTrx.length - 1))
            {
                hql += (":transactionTypeCT" + i + ", ");
            }
            else
            {
                hql += (":transactionTypeCT" + i);
            }
        }

        hql += ")";

        hql += " order by editTrx.EffectiveDate asc";

        Map params = new HashMap();

        params.put("segmentFK", segmentPK);

        params.put("runDate", runDateTime);

        for (int i = 0; i < historyTrx.length; i++)
        {
            String currentTranscationTypeCT = historyTrx[i];

            params.put("transactionTypeCT" + i, currentTranscationTypeCT);
        }

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        return (EDITTrxHistory[]) results.toArray(new EDITTrxHistory[results.size()]);
    }

    /**
     * The composed Dom4j Document.
     * @see #buildDocument(long, edit.common.EDITDate, String)
     * @return
     */
    public Document getDocument()
    {
        return document;
    }

    public long getHighestEquityIndexHedgeCounter()
    {
        return highestSequenceNumber;
    }

    // utility method to get the max sequence number from equity index hedge detail records.
    private void determineHighestEquityIndexHedgeCounter(NaturalDocVO currentNaturalDocVO)
    {
        EquityIndexHedgeDetailVO equityIndexHedgeDetailVO = null;

        for (int i = 0; i < currentNaturalDocVO.getEquityIndexHedgeDetailVOCount(); i++)
        {
            equityIndexHedgeDetailVO = currentNaturalDocVO.getEquityIndexHedgeDetailVO(i);

            if (equityIndexHedgeDetailVO.getEquityIndexHedgeDetailPK() > highestSequenceNumber)
            {
                highestSequenceNumber = equityIndexHedgeDetailVO.getEquityIndexHedgeDetailPK();
            }
        }
    }

    /**
     * Getter.
     * @return
     */
    public long getHighestEquityIndexHedgeCount()
    {
        return highestSequenceNumber;
    }

    /**
     * Getter.
     * @return
     */
    public int getEquityIndexHedgeDetailVOCount()
    {
        return equityIndexHedgeDetailVOCount;
    }

    /**
     * Composes this Segment as an DOM Element. Pojos mapped to the Element include:
     * Segment
     * Payout
     * AgentHierarchy
     * AgentSnapshot
     * Investment
     * InvestmentAllocation
     * ContractClient
     * NOT ClientRole (added to Hibernate cache as a convenience)
     * Bucket
     * AnnualizedSubBuckets
     * Deposits
     * BucketAllocation
     * @return
     */
    private Element composeSegmentElement(Segment segment)
    {
        Element segmentElement = SessionHelper.mapToElement(segment, SessionHelper.EDITSOLUTIONS, false, false);

        // Payout
        SessionHelper.addPojoToElement(segment.getPayout(), segmentElement);

        // Deposits
        Set deposits = segment.getDeposits();

        for (Iterator iterator = deposits.iterator(); iterator.hasNext();)
        {
            Deposits deposit = (Deposits) iterator.next();

            SessionHelper.addPojoToElement(deposit, segmentElement);
        }

        // ContractClient
        Set contractClients = segment.getContractClients();

        for (Iterator iterator = contractClients.iterator(); iterator.hasNext();)
        {
            ContractClient contractClient = (ContractClient) iterator.next();

            SessionHelper.addPojoToElement(contractClient, segmentElement);
        }

        // AgentHierarchy and AgentSnapshot
        Set agentHierarchies = segment.getAgentHierarchies();

        for (Iterator iterator = agentHierarchies.iterator(); iterator.hasNext();)
        {
            AgentHierarchy agentHierarchy = (AgentHierarchy) iterator.next();

            Element agentHierarchyElement = SessionHelper.addPojoToElement(agentHierarchy, segmentElement);

            Set agentSnapshots = agentHierarchy.getAgentSnapshots();

            for (Iterator iterator1 = agentSnapshots.iterator(); iterator1.hasNext();)
            {
                AgentSnapshot agentSnapshot = (AgentSnapshot) iterator1.next();

                SessionHelper.addPojoToElement(agentSnapshot, agentHierarchyElement);
            }
        }

        // Investments, InvestmentAllocation, Bucket, AnnualizedSubBucket, BucketAllocation
        Set investments = segment.getInvestments();

        for (Iterator iterator = investments.iterator(); iterator.hasNext();)
        {
            Investment investment = (Investment) iterator.next();

            Element investmentElement = SessionHelper.addPojoToElement(investment, segmentElement);

            Set investmentAllocations = investment.getInvestmentAllocations();

            for (Iterator iterator1 = investmentAllocations.iterator(); iterator1.hasNext();)
            {
                InvestmentAllocation investmentAllocation = (InvestmentAllocation) iterator1.next();

                SessionHelper.addPojoToElement(investmentAllocation, investmentElement);
            }

            Set buckets = investment.getBuckets();

            Iterator iterator1 = buckets.iterator();

            while (iterator1.hasNext())
            {
                Bucket bucket = (Bucket) iterator1.next();

                Element bucketElement = SessionHelper.addPojoToElement(bucket, investmentElement);

                Set annualizedSubBuckets = bucket.getAnnualizedSubBuckets();

                Iterator iterator2 = annualizedSubBuckets.iterator();

                while(iterator2.hasNext())
                {
                    AnnualizedSubBucket annualizedSubBucket = (AnnualizedSubBucket) iterator2.next();

                    SessionHelper.addPojoToElement(annualizedSubBucket, bucketElement);
                }

                Set bucketAllocations = bucket.getBucketAllocations();

                iterator2 = bucketAllocations.iterator();

                while (iterator2.hasNext())
                {
                    BucketAllocation bucketAllocation = (BucketAllocation) iterator2.next();

                    SessionHelper.addPojoToElement(bucketAllocation, bucketElement);
                }
            }
        }

        return segmentElement;
    }

    /**
     * Retrieves all rider segments for this segment
     * @return
     */
    private Element[] getRiderSegments()
    {
        Set riderSegments = segment.getSegments();

        List riderElements = new ArrayList();

        Iterator it = riderSegments.iterator();

        while (it.hasNext())
        {
            Segment riderSegment = (Segment) it.next();

            Element riderSegmentElement = SessionHelper.mapToElement(riderSegment, SessionHelper.EDITSOLUTIONS, false, false);

            riderElements.add(riderSegmentElement);
        }

        return (Element[]) riderElements.toArray(new Element[riderElements.size()]);
    }
}
