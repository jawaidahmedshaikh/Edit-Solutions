/*
 * User: gfrosti
 * Date: Jun 27, 2005
 * Time: 2:56:28 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package codetable;

import businesscalendar.*;

import client.*;

import contract.*;

import edit.common.*;
import edit.common.exceptions.EDITEventException;

import edit.common.vo.*;

import edit.services.db.hibernate.*;

import engine.component.*;

import event.*;

import org.dom4j.*;

import org.dom4j.tree.*;

import role.*;

import java.util.*;



public class PRASEDocHelper
{
    public static final int FINANCIAL_TRX = 0;
    public static final int CHECK_TRX = 1;
    public static final int BONUS_CHECK_TRX = 2;

    /**
     * Startings with an EDITTrxPK, builds the following composite:
     * Segment
     * Segment.Payout
     * Segment.Life
     * Segment.AgentHierarchy
     * Segment.Deposits
     * Segment.InherentRider
     * Segment.RequiredMinDistribution
     * @param editTrxPK
     * @return
     */
    public static final PRASEDocResult buildSegment(Long editTrxPK, ContractSetup contractSetup)
    {
        String hql = "select segment" +
                " from EDITTrx editTrx " +
                " join editTrx.ClientSetup clientSetup " +
                " join clientSetup.ContractSetup contractSetup" +
                " join contractSetup.Segment segment" +
                " left join fetch segment.Investments investment" +
                " left join fetch investment.InvestmentAllocations" +
                " left join fetch investment.Buckets bucket" +
                " left join fetch bucket.AnnualizedSubBuckets" +
                " left join fetch segment.Payouts" +
                " left join fetch segment.Lifes" +
                " left join fetch segment.InherentRiders" +
                " left join fetch segment.RequiredMinDistributions" +
                " left join fetch segment.AgentHierarchies" +
                " left join fetch segment.Deposits" +
                " where editTrx.EDITTrxPK = :editTrxPK";

        Map params = new HashMap();

        params.put("editTrxPK", editTrxPK);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        // Segment
        Segment segment = (Segment) results.get(0);

        Element segmentElement = SessionHelper.mapToElement(segment, SessionHelper.EDITSOLUTIONS, false, false);

        // Payout
        Payout payout = segment.getPayout();

        if (payout != null)
        {
            Element payoutElement = SessionHelper.mapToElement(payout, SessionHelper.EDITSOLUTIONS, false, false);

            segmentElement.add(payoutElement);
        }

        // Life
        Life life = segment.getLife();

        if (life != null)
        {
            Element lifeElement = SessionHelper.mapToElement(life, SessionHelper.EDITSOLUTIONS, false, false);

            segmentElement.add(lifeElement);
        }

        // InherentRider
        InherentRider inherentRider = segment.getInherentRider();

        if (inherentRider != null)
        {
            Element inherentRiderElement = SessionHelper.mapToElement(inherentRider, SessionHelper.EDITSOLUTIONS, false, false);

            segmentElement.add(inherentRiderElement);
        }

        // RequiredMinDistribution
        RequiredMinDistribution requiredMinDistribution = segment.getRequiredMinDistribution();

        if (requiredMinDistribution != null)
        {
            Element requiredMinDistributionElement = SessionHelper.mapToElement(requiredMinDistribution, SessionHelper.EDITSOLUTIONS, false, false);

            segmentElement.add(requiredMinDistributionElement);
        }

        // Deposits
        Set deposits = segment.getDeposits();

        if (deposits != null)
        {
            for (Iterator iterator = deposits.iterator(); iterator.hasNext();)
            {
                Deposits currentDeposits = (Deposits) iterator.next();

                Element currentElementDeposits = SessionHelper.mapToElement(currentDeposits, SessionHelper.EDITSOLUTIONS, false, false);

                segmentElement.add(currentElementDeposits);
            }
        }

        // AgentHierarchy
        Set agentHierarchies = segment.getAgentHierarchies();

        for (Iterator iterator = agentHierarchies.iterator(); iterator.hasNext();)
        {
            AgentHierarchy agentHierarchy = (AgentHierarchy) iterator.next();

            Element agentHierarchyElement = SessionHelper.mapToElement(agentHierarchy, SessionHelper.EDITSOLUTIONS, false, false);

            segmentElement.add(agentHierarchyElement);
        }

        // Investment
        Set investments = segment.getInvestments();

        Set investmentAllocationOverrides = null;
        if (contractSetup != null)
        {
            investmentAllocationOverrides = checkForInvestmentAllocationOverrides(contractSetup);
        }

        if (investmentAllocationOverrides == null || investmentAllocationOverrides.size() == 0)
        {
            for (Iterator iterator = investments.iterator(); iterator.hasNext();)
            {
                Investment investment = (Investment) iterator.next();

                Element investmentElement = SessionHelper.mapToElement(investment, SessionHelper.EDITSOLUTIONS, false, false);

                segmentElement.add(investmentElement);

                // InvestmentAllocation
                InvestmentAllocation investmentAllocation = investment.getInvestmentAllocation();

                if (investmentAllocation != null)
                {
                    Element investmentAllocationElement = SessionHelper.mapToElement(investmentAllocation, SessionHelper.EDITSOLUTIONS, false, false);

                    investmentElement.add(investmentAllocationElement);
                }

                // Bucket
                Set buckets = investment.getBuckets();

                for (Iterator iterator1 = buckets.iterator(); iterator1.hasNext();)
                {
                    Bucket bucket = (Bucket) iterator1.next();

                    Element bucketElement = SessionHelper.mapToElement(bucket, SessionHelper.EDITSOLUTIONS, false, false);

                    investmentElement.add(bucketElement);

                    // AnnualizedSubBucket
                    Set annualizedSubBuckets = bucket.getAnnualizedSubBuckets();

                    for (Iterator iterator2 = annualizedSubBuckets.iterator(); iterator2.hasNext();)
                    {
                        AnnualizedSubBucket annualizedSubBucket = (AnnualizedSubBucket) iterator2.next();

                        Element annualizedSubBucketElement = SessionHelper.mapToElement(annualizedSubBucket, SessionHelper.EDITSOLUTIONS, false, false);

                        bucketElement.add(annualizedSubBucketElement);
                    }
                }
            }
        }
        else
        {
            setInvestmentAllocationOverrides(investments, investmentAllocationOverrides, segmentElement);

        }

        PRASEDocResult praseDocResult = new PRASEDocResult(segment, segmentElement);

        return praseDocResult;
    }

    /**
     * Overlay InvestmentAllocation with any InvestmentAllocationOverrides
     * @param contractSetups
     */
    private static Set checkForInvestmentAllocationOverrides(ContractSetup contractSetup)
    {
        Set investmentAllocationOverrides = null;

        String hql = "select contractSetup, investmentAllocationOverrides" +
                     " from ContractSetup contractSetup " +
                     " left join fetch contractSetup.InvestmentAllocationOverrides investmentAllocationOverrides" +
                     " where contractSetup = :contractSetup";

        Map params = new HashMap();
        params.put("contractSetup", contractSetup);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);
        if (!results.isEmpty())
        {
            contractSetup = (ContractSetup) results.get(0) ;

            investmentAllocationOverrides = contractSetup.getInvestmentAllocationOverrides();
        }

        return investmentAllocationOverrides;
    }

    /**
     * Overlay InvestmentAllocation with any InvestmentAllocationOverrides
     * @param investment
     * @param investmentAllocation
     * @param contractSetups
     */
    private static void setInvestmentAllocationOverrides(Set investments, Set investmentAllocationOverrides, Element segmentElement )
    {

       for (Iterator iterator1 = investmentAllocationOverrides.iterator(); iterator1.hasNext();)
       {
            InvestmentAllocationOverride investmentAllocationOvrd = (InvestmentAllocationOverride)iterator1.next();
            Investment investment = investmentAllocationOvrd.getInvestment();
            Element investmentElement = SessionHelper.mapToElement(investment, SessionHelper.EDITSOLUTIONS, false, false);
            segmentElement.add(investmentElement);

            InvestmentAllocation investmentAllocation = investmentAllocationOvrd.getInvestmentAllocation();
            Element investmentAllocationElement = SessionHelper.mapToElement(investmentAllocation, SessionHelper.EDITSOLUTIONS, false, false);
            investmentElement.add(investmentAllocationElement);

            // Bucket
            Set buckets = investment.getBuckets();

           if (buckets != null)
           {
               for (Iterator iterator2 = buckets.iterator(); iterator2.hasNext();)
               {
                   Bucket bucket = (Bucket) iterator2.next();

                   Element bucketElement = SessionHelper.mapToElement(bucket, SessionHelper.EDITSOLUTIONS, false, false);

                   investmentElement.add(bucketElement);

                   // AnnualizedSubBucket
                   Set annualizedSubBuckets = bucket.getAnnualizedSubBuckets();

                   for (Iterator iterator3 = annualizedSubBuckets.iterator(); iterator3.hasNext();)
                   {
                       AnnualizedSubBucket annualizedSubBucket = (AnnualizedSubBucket) iterator3.next();

                       Element annualizedSubBucketElement = SessionHelper.mapToElement(annualizedSubBucket, SessionHelper.EDITSOLUTIONS, false, false);

                       bucketElement.add(annualizedSubBucketElement);
                   }
               }
           }
       }
    }

    /**
     * Starting with EDITTrxHistory as the root, builds the following composite:
     * EDITrxHistory
     * EDITrxHistory.FinancialHistory
     * EDITTrxHistory.ChargeHistory
     * EDITrxHistory.BucketHistory
     * EDITrxHistory.BucketHistory.BucketChargeHistory
     * EDITrxHistory.WithholdingHistory
     * EDITTrxHistory.InSuspense
     * EDITrxHistory.CommissionHistory
     * EDITTrxHistory.InvestmentHistory
     * @param editTrxPK
     * @return
     */
    public static final PRASEDocResult buildEDITTrxHistory(Long editTrxPK)
    {
        String hql = "select editTrxHistory " + " from EDITTrxHistory editTrxHistory" +
                   " left join fetch editTrxHistory.FinancialHistories" +
                   " left join fetch editTrxHistory.ChargeHistories" +
                   " left join fetch editTrxHistory.BucketHistories bucketHistory" +
                   " left join fetch editTrxHistory.BucketHistories" +
                   " left join fetch editTrxHistory.WithholdingHistories" +
                   " left join fetch editTrxHistory.InSuspenses" +
                   " left join fetch editTrxHistory.CommissionHistories" +
                   " left join fetch editTrxHistory.InvestmentHistories" +
                   " left join fetch bucketHistory.BucketChargeHistories " +
                   " where editTrxHistory.EDITTrx.EDITTrxPK = :editTrxPK";

        Map params = new HashMap();

        params.put("editTrxPK", editTrxPK);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        // EDITTrxHistory
        EDITTrxHistory editTrxHistory = (EDITTrxHistory) results.get(0);

        Element editTrxHistoryElement = SessionHelper.mapToElement(editTrxHistory, SessionHelper.EDITSOLUTIONS, false, false);

        // FinancialHistory
        FinancialHistory financialHistory = editTrxHistory.getFinancialHistory();

        if (financialHistory != null)
        {
            Element financialHistoryElement = SessionHelper.mapToElement(financialHistory, SessionHelper.EDITSOLUTIONS, false, false);

            editTrxHistoryElement.add(financialHistoryElement);
        }

        // ChargeHistories
        Set chargeHistories = editTrxHistory.getChargeHistories();

        for (Iterator iterator = chargeHistories.iterator(); iterator.hasNext();)
        {
            ChargeHistory chargeHistory = (ChargeHistory) iterator.next();

            Element chargeHistoryElement = SessionHelper.mapToElement(chargeHistory, SessionHelper.EDITSOLUTIONS, false, false);

            editTrxHistoryElement.add(chargeHistoryElement);
        }

        // BucketHistories
        Set bucketHistories = editTrxHistory.getBucketHistories();

        for (Iterator iterator = bucketHistories.iterator(); iterator.hasNext();)
        {
            BucketHistory bucketHistory = (BucketHistory) iterator.next();

            Element bucketHistoryElement = SessionHelper.mapToElement(bucketHistory, SessionHelper.EDITSOLUTIONS, false, false);

            editTrxHistoryElement.add(bucketHistoryElement);

            Set bucketChargeHistories = bucketHistory.getBucketChargeHistories();

            // BucketChargeHistories
            for (Iterator iterator2 = bucketChargeHistories.iterator(); iterator2.hasNext();)
            {
                BucketChargeHistory bucketChargeHistory = (BucketChargeHistory) iterator2.next();

                Element bucketChargeHistoryElement = SessionHelper.mapToElement(bucketChargeHistory, SessionHelper.EDITSOLUTIONS, false, false);

                bucketHistoryElement.add(bucketChargeHistoryElement);
            }
        }

        // WithholdingHistory
        WithholdingHistory withholdingHistory = editTrxHistory.getWithholdingHistory();

        if (withholdingHistory != null)
        {
            Element withholdingHistoryElement = SessionHelper.mapToElement(withholdingHistory, SessionHelper.EDITSOLUTIONS, false, false);

            editTrxHistoryElement.add(withholdingHistoryElement);
        }

        // InSuspense
        InSuspense inSuspense = editTrxHistory.getInSuspense();

        if (inSuspense != null)
        {
            Element inSuspenseElement = SessionHelper.mapToElement(inSuspense, SessionHelper.EDITSOLUTIONS, false, false);

            editTrxHistoryElement.add(inSuspenseElement);
        }

        // CommissionHistories
        Set commissionHistoryElements = editTrxHistory.getCommissionHistories();

        for (Iterator iterator = commissionHistoryElements.iterator(); iterator.hasNext();)
        {
            CommissionHistory commissionHistory = (CommissionHistory) iterator.next();

            Element commissionHistoryElement = SessionHelper.mapToElement(commissionHistory, SessionHelper.EDITSOLUTIONS, false, false);

            editTrxHistoryElement.add(commissionHistoryElement);
        }

        // InvestmentHistories
        Set investmentHistoryElements = editTrxHistory.getInvestmentHistories();

        for (Iterator iterator = investmentHistoryElements.iterator(); iterator.hasNext();)
        {
            InvestmentHistory investmentHistory = (InvestmentHistory) iterator.next();

            Element investmentHistoryElement = SessionHelper.mapToElement(investmentHistory, SessionHelper.EDITSOLUTIONS, false, false);

            editTrxHistoryElement.add(investmentHistoryElement);
        }

        PRASEDocResult praseDocResult = new PRASEDocResult(editTrxHistory, editTrxHistoryElement);

        return praseDocResult;
    }

    /**
     * Builds the following DOM4J Element composite:
     * GroupSetup.ContractSetup.ClientSetup.EDITTrx.
     *
     * Additionally, the following child elements are included:
     * GroupSetup.ScheduledEvent
     * GroupSetup.Charge
     * ContractSetup.InvestmentAllocationOverride
     * @param editTrxPK
     * @return
     */
    public static final PRASEDocResult buildGroupSetup(Long editTrxPK)
    {
        String hql = "select editTrx" +
                " from EDITTrx editTrx" +
                " join fetch editTrx.ClientSetup clientSetup" +
                " join fetch clientSetup.ContractSetup contractSetup" +
                " join fetch contractSetup.GroupSetup groupSetup" +
                " left join fetch contractSetup.InvestmentAllocationOverrides" +
                " left join fetch groupSetup.ScheduledEvents" +
                " left join fetch groupSetup.Charges" +
                " where editTrx.EDITTrxPK = :editTrxPK";

        Map params = new HashMap();

        params.put("editTrxPK", editTrxPK);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        // The root of this chain is EDITTrx.ClientSetup.ContractSetup.GroupSetup
        EDITTrx editTrx = (EDITTrx) results.get(0);

        // GroupSetup
        GroupSetup groupSetup = editTrx.getClientSetup().getContractSetup().getGroupSetup();

        Element groupSetupElement = SessionHelper.mapToElement(groupSetup, SessionHelper.EDITSOLUTIONS, false, false);

        // ScheduledEvents
        Set scheduledEvents = groupSetup.getScheduledEvents();

        if (scheduledEvents != null)
        {
            for (Iterator iterator = scheduledEvents.iterator(); iterator.hasNext();)
            {
                ScheduledEvent scheduledEvent = (ScheduledEvent) iterator.next();

                Element scheduledEventElement = SessionHelper.mapToElement(scheduledEvent, SessionHelper.EDITSOLUTIONS, false, false);

                groupSetupElement.add(scheduledEventElement);
            }
        }

        // Charges
        Set charges = groupSetup.getCharges();

        if (charges != null)
        {
            for (Iterator iterator = charges.iterator(); iterator.hasNext();)
            {
                Charge charge = (Charge) iterator.next();

                Element chargeElement = SessionHelper.mapToElement(charge, SessionHelper.EDITSOLUTIONS, false, false);

                groupSetupElement.add(chargeElement);
            }
        }

        // ContractSetup
        ContractSetup contractSetup = editTrx.getClientSetup().getContractSetup();

        Element contractSetupElement = SessionHelper.mapToElement(contractSetup, SessionHelper.EDITSOLUTIONS, false, false);

        groupSetupElement.add(contractSetupElement);

        // InvestmentAllocationOverrides
        Set investmentAllocationOverrides = contractSetup.getInvestmentAllocationOverrides();

        if (investmentAllocationOverrides != null)
        {
            for (Iterator iterator = investmentAllocationOverrides.iterator(); iterator.hasNext();)
            {
                InvestmentAllocationOverride investmentAllocationOverride = (InvestmentAllocationOverride) iterator.next();

                Element investmentAllocationOverrideElement = SessionHelper.mapToElement(investmentAllocationOverride, SessionHelper.EDITSOLUTIONS, false, false);

                contractSetupElement.add(investmentAllocationOverrideElement);
            }
        }

        // ClientSetup
        ClientSetup clientSetup = editTrx.getClientSetup();

        Element clientSetupElement = SessionHelper.mapToElement(clientSetup, SessionHelper.EDITSOLUTIONS, false, false);

        contractSetupElement.add(clientSetupElement);

        // EDITTrx
        Element editTrxElement = SessionHelper.mapToElement(editTrx, SessionHelper.EDITSOLUTIONS, false, false);

        clientSetupElement.add(editTrxElement);

        PRASEDocResult praseDocResult = new PRASEDocResult(groupSetup, groupSetupElement);

        return praseDocResult;
    }

    /**
     * Builds the base Client.
     *
     * Client.ClientDetail
     * Client.ClientDetail.ClientAddress (all of them, except for terminated ones where the termination date is non null)
     * Client.ClientDetail.Preference
     * Client.ClientDetail.BankAccountInformation
     * Client.ClientDetail.TaxInformation
     * Client.ClientDetail.TaxInformation.TaxProfile
     * Client.ClientRole (roleTypeCT only)
     *
     * @param clientRole
     * @param clientRoleIsComposed true if the ClientRole entity already contains all necessary associations in Session
     * @return
     */
    public static PRASEDocResult buildClient(ClientRole clientRole, boolean clientRoleIsComposed) throws EDITEventException
    {
        if (!clientRoleIsComposed)
        {
            composeClientRole(clientRole);
        }

        // ClientDetail
        ClientDetail clientDetail = clientRole.getClientDetail();

        Element clientDetailElement = SessionHelper.mapToElement(clientDetail, SessionHelper.EDITSOLUTIONS, false, false);

        // ClientAddress
        Set clientAddresses = clientDetail.getClientAddresses();

        for (Iterator iterator = clientAddresses.iterator(); iterator.hasNext();)
        {
            ClientAddress clientAddress = (ClientAddress) iterator.next();

            if (clientAddress.getTerminationDate() == null)
            {
                Element clientAddressElement = SessionHelper.mapToElement(clientAddress, SessionHelper.EDITSOLUTIONS, false, false);

                clientDetailElement.add(clientAddressElement);
            }
        }

        // Preference
        Set preferences = clientDetail.getPreferences();

        for (Iterator iterator = preferences.iterator(); iterator.hasNext();)
        {
            Preference preference = (Preference) iterator.next();

            Element preferenceElement = SessionHelper.mapToElement(preference, SessionHelper.EDITSOLUTIONS, false, false);

            clientDetailElement.add(preferenceElement);
        }

        // TaxInformation
        Set taxInformations = clientDetail.getTaxInformations();

        for (Iterator iterator = taxInformations.iterator(); iterator.hasNext();)
        {
            TaxInformation taxInformation = (TaxInformation) iterator.next();

            Element taxInformationElement = SessionHelper.mapToElement(taxInformation, SessionHelper.EDITSOLUTIONS, false, false);

            clientDetailElement.add(taxInformationElement);

            Set taxProfiles = taxInformation.getTaxProfiles();

            for (Iterator iterator1 = taxProfiles.iterator(); iterator1.hasNext();)
            {
                TaxProfile taxProfile = (TaxProfile) iterator1.next();

                Element taxProfileElement = SessionHelper.mapToElement(taxProfile, SessionHelper.EDITSOLUTIONS, false, false);

                taxInformationElement.add(taxProfileElement);
            }
        }

        // Client
        Element clientElement = new DefaultElement("ClientVO");

        Element roleTypeCTElement = new DefaultElement("RoleTypeCT");

        roleTypeCTElement.setText(clientRole.getRoleTypeCT());

        clientElement.add(roleTypeCTElement);

        clientElement.add(clientDetailElement);

        PRASEDocResult praseDocResult = new PRASEDocResult(null, clientElement);

        return praseDocResult;
    }

    /**
     * Composes the specified ClientRole with:
     * ClientDetail
     * ClientAddress (active only)
     * Preference
     * BankAccountInformation
     * TaxInformation
     * TaxProfile
     *
     * @param clientRole
     */
    private static void composeClientRole(ClientRole clientRole) throws EDITEventException
    {
        String hql = " select clientRole from ClientRole clientRole" +
                    " join fetch clientRole.ClientDetail clientDetail" +
                    " left join fetch clientDetail.ClientAddresses clientAddress" +
                    " left join fetch clientDetail.Preferences" +
                    " left join fetch clientDetail.BankAccountInformations" +
                    " left join fetch clientDetail.TaxInformations taxInformation" +
                    " left join fetch taxInformation.TaxProfiles" +
                    " where clientRole = :clientRole";

        Map params = new HashMap();

        params.put("clientRole", clientRole);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS); // No need to return the results - just composing the specified ClientRole.

        if (results.isEmpty())
        {
            EDITEventException editEventException = new EDITEventException("ClientRole And Its Related Entities Could Not Be Built [ClientRolePK: " + clientRole.getClientRolePK() + "]");

            editEventException.setErrorNumber(EDITEventException.CONSTANT_NO_DATA_FOUND);

            throw editEventException;
        }
    }

    /**
     * Builds the Client composition varied by transaction type.
     *
     * If the transaction type is a "Check Transaction", the composition is as follows:
     * Client.ClientDetail
     * Client.ClientDetail.ClientAddress (all of them, except for terminated ones where the termination date is non null)
     * Client.ClientDetail.Preference
     * Client.ClientDetail.BankAccountInformation
     * Client.ClientDetail.TaxInformation
     * Client.ClientDetail.TaxInformation.TaxProfile
     * Client.ClientRole (roleTypeCT only)
     *
     * If the transaction type is a "Financial Transaction", then the following is composed in <b>addition</b> to the
     * information composed for the Check Transaction.
     *
     * Client.ContractClient
     * Client.ContractClient.ContractClientAllocation
     * Client.ContractClient.Withholding
     *
     * @param editTrxPK
     * @param trxType
     * @return
     */
    public static final PRASEDocResult buildClient(Long editTrxPK, int trxType) throws EDITEventException
    {
        // EDITTrx
        EDITTrx editTrx = buildEDITTrx(editTrxPK, trxType);

        // ClientRole
        ClientRole clientRole = null;

        if (trxType == FINANCIAL_TRX)
        {
            clientRole = editTrx.getClientSetup().getContractClient().getClientRole();
        }
        else if (trxType == CHECK_TRX)
        {
            clientRole = editTrx.getClientSetup().getClientRole();
        }

        PRASEDocResult praseDocResult = buildClient(clientRole, true);

        // ContractClient (if a Financial Trx)
        Element contractClientElement = null;

        if (trxType == FINANCIAL_TRX)
        {
            ContractClient contractClient = editTrx.getClientSetup().getContractClient();

            contractClientElement = SessionHelper.mapToElement(contractClient, SessionHelper.EDITSOLUTIONS, false, false);

            ContractClientAllocation contractClientAllocation = contractClient.getContractClientAllocation();

            if (contractClientAllocation != null)
            {
                Element contractClientAllocationElement = SessionHelper.mapToElement(contractClientAllocation, SessionHelper.EDITSOLUTIONS, false, false);

                contractClientElement.add(contractClientAllocationElement);
            }
            else
            {
                //check if an override exists, to get the correct allocation
                Set contractClientAllocationOvrds = editTrx.getClientSetup().getContractClientAllocationOvrds();

                if (contractClientAllocationOvrds != null)
                {
                    for (Iterator iterator = contractClientAllocationOvrds.iterator(); iterator.hasNext();)
                    {
                        ContractClientAllocationOvrd contractClientAllocationOvrd =  (ContractClientAllocationOvrd)iterator.next();
                        contractClientAllocation=  contractClientAllocationOvrd.getContractClientAllocation();

                        Element contractClientAllocationElement = SessionHelper.mapToElement(contractClientAllocation, SessionHelper.EDITSOLUTIONS, false, false);
                        contractClientElement.add(contractClientAllocationElement);
                        break;
                    }
                }
            }

            Set withholdings = contractClient.getWithholdings();

            for (Iterator iterator = withholdings.iterator(); iterator.hasNext();)
            {
                Withholding withholding = (Withholding) iterator.next();

                Element withholdingElement = SessionHelper.mapToElement(withholding, SessionHelper.EDITSOLUTIONS, false, false);

                contractClientElement.add(withholdingElement);
            }
        }

        if (contractClientElement != null)
        {
            Element clientElement = praseDocResult.getElement();

            clientElement.add(contractClientElement);
        }

        return praseDocResult;
    }

    /**
     * Helper method.
     * @see #buildClient(Long, int)
     * @param editTrxPK
     */
    private static final EDITTrx buildEDITTrx(Long editTrxPK, int trxType) throws EDITEventException
    {
        EDITTrx editTrx = null;

        String hql = null;

        switch (trxType)
        {
        case FINANCIAL_TRX:
            hql =   " select editTrx" + " from EDITTrx editTrx" +
                    " join fetch editTrx.ClientSetup clientSetup" +
                    " join fetch clientSetup.ContractClient contractClient" +
                    " left join fetch contractClient.ContractClientAllocations" +
                    " left join fetch clientSetup.ContractClientAllocationOvrds contractClientAllocationOverride" +
                    " left join fetch contractClientAllocationOverride.ContractClientAllocation" +
                    " left join fetch contractClient.Withholdings" +
                    " join fetch contractClient.ClientRole clientRole" +
                    " join fetch clientRole.ClientDetail clientDetail" +
                    " left join fetch clientDetail.ClientAddresses clientAddress" +
                    " left join fetch clientDetail.Preferences" +
                    " left join fetch clientDetail.BankAccountInformations" +
                    " left join fetch clientDetail.TaxInformations taxInformation" +
                    " left join fetch taxInformation.TaxProfiles" +
                    " where editTrx.EDITTrxPK = :editTrxPK" +
                    " and clientAddress.TerminationDate is not null"; // Only active addresses

            break;

        case CHECK_TRX:
            hql =   " select editTrx" + " from EDITTrx editTrx" +
                    " join fetch editTrx.ClientSetup clientSetup" +
                    " join fetch clientSetup.ClientRole clientRole" +
                    " join fetch clientRole.ClientDetail clientDetail" +
                    " left join fetch clientDetail.ClientAddresses clientAddress" +
                    " left join fetch clientDetail.Preferences" +
                    " left join fetch clientDetail.BankAccountInformations" +
                    " left join fetch clientDetail.TaxInformations taxInformation" +
                    " left join fetch taxInformation.TaxProfiles" +
                    " where editTrx.EDITTrxPK = :editTrxPK" +
                    " and clientAddress.TerminationDate is not null"; // Only active addresses

            break;
        }

        Map params = new HashMap();

        params.put("editTrxPK", editTrxPK);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        if (results.isEmpty())
        {
            // If the results are empty it could be because of any reason -- but potentially may be ClientAddress
            // is not available and TerminationDate in ClienAddress being checked. The other probabilities are very low.
            EDITEventException editEventException = new EDITEventException("No Data Found");

            editEventException.setErrorNumber(EDITEventException.CONSTANT_NO_DATA_FOUND);

            throw editEventException;
        }

        editTrx = (EDITTrx) results.get(0);

        return editTrx;
    }

    /**
     * Builds all InvestmentArray(s) for the specified EDITTrx as a List of DOM Elements.
     * @param editTrxPK
     * @return
     */
    public static final List buildInvestmentArray(Long editTrxPK)
    {
        String hql = "select segment" +
                " from Segment segment" +
                " join fetch segment.Investments investment" +
                " join fetch investment.InvestmentHistories investmentHistory" +
                " join fetch investment.Buckets bucket" +
                " join fetch investmentHistory.EDITTrxHistory editTrxHistory" +
                " join fetch editTrxHistory.BucketHistories" +
                " join fetch editTrxHistory.EDITTrx editTrx" +
                " where  editTrx.EDITTrxPK = :editTrxPK";

        Map params = new HashMap();

        params.put("editTrxPK", editTrxPK);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        Segment segment = (Segment) results.get(0);

        EDITTrx editTrx = (EDITTrx) SessionHelper.get(EDITTrx.class, editTrxPK, SessionHelper.EDITSOLUTIONS);

        EDITDate currMFEffDate = editTrx.getEffectiveDate();
        EDITDate priorMFEffDate = getPriorMFEffDate(currMFEffDate);
        EDITDate editTrxFromDate = priorMFEffDate;
        EDITDate polEffDate = segment.getEffectiveDate();

        if (priorMFEffDate.before(polEffDate))
        {
            editTrxFromDate = segment.getEffectiveDate();
            editTrxFromDate = editTrxFromDate.subtractDays(1);
            priorMFEffDate = editTrxFromDate;
        }

        BusinessDay[] businessDays = BusinessDay.findBy_Range_Inclusive(priorMFEffDate, currMFEffDate);

        Set allInvestments = segment.getInvestments();

        List investmentArrays = new ArrayList();

        for (Iterator investmentIterator = allInvestments.iterator(); investmentIterator.hasNext();)
        {
            Investment currentInvestment = (Investment) investmentIterator.next();

            Long filteredFundFK = currentInvestment.getFilteredFundFK();

            EDITDate priorUVEffDate = null;

            EDITBigDecimal priorCumUnits = new EDITBigDecimal();

            for (int i = 0; i < businessDays.length; i++)
            {
                EDITDate businessDate = businessDays[i].getBusinessDate();

                if ((i == 0) && !businessDate.equals(priorMFEffDate))
                {
                    InvestmentArrayVO investmentArrayVO = buildInvestmentArrayVO(priorMFEffDate, currentInvestment.getInvestmentPK(), new EDITBigDecimal(), new EDITBigDecimal());

                    investmentArrays.add(mapInvestmentArrayVOAsElement(investmentArrayVO));

                    priorUVEffDate = priorMFEffDate;
                }

                UnitValuesVO[] unitValuesVO = null;

                EDITBigDecimal unitValue = null;//new EDITBigDecimal();

                boolean exactMatchFound = false;

                EDITBigDecimal cumUnits = null;//new EDITBigDecimal();

                Set investmentHistories = currentInvestment.getInvestmentHistories();

                for (Iterator investmentHistoryIterator = investmentHistories.iterator();
                        investmentHistoryIterator.hasNext();)
                {
                    InvestmentHistory currentInvestmentHistory = (InvestmentHistory) investmentHistoryIterator.next();

                    long chargeCodeFK = currentInvestmentHistory.getChargeCodeFK() == null?0:currentInvestmentHistory.getChargeCodeFK().longValue();

                    unitValuesVO = new LookupComponent().getUnitValuesByFund_ChargeCode_Date(filteredFundFK.longValue(), chargeCodeFK, businessDate.getFormattedDate(), "Forward");

                    if ((unitValuesVO != null) && (unitValuesVO.length > 0))
                    {
                        unitValue = new EDITBigDecimal(unitValuesVO[0].getUnitValue());
                    }
                    else
                    {
                        unitValue = new EDITBigDecimal();
                    }

                    EDITTrx currentEDITTrx = currentInvestmentHistory.getEDITTrxHistory().getEDITTrx();

                    EDITDate trxEffDate = currentEDITTrx.getEffectiveDate();

                    if (priorUVEffDate != null)
                    {
                        if (trxEffDate.after(priorUVEffDate) && trxEffDate.before(businessDate))
                        {
                            EDITDate nextUVEffDate = priorUVEffDate;

                            int noDaysToGenerate = trxEffDate.getElapsedDays(priorUVEffDate) - 1;

                            for (int j = 0; j < noDaysToGenerate; j++)
                            {
                                nextUVEffDate.addDays(1);

                                InvestmentArrayVO nonBizDayInvArrayVO = buildInvestmentArrayVO(nextUVEffDate, currentInvestment.getInvestmentPK(), priorCumUnits, unitValue);

                                investmentArrays.add(mapInvestmentArrayVOAsElement(nonBizDayInvArrayVO));
                            }

                            Set bucketHistories = currentInvestmentHistory.getEDITTrxHistory().getBucketHistories();

                            cumUnits = getCumUnitsForInvestmentArray(bucketHistories);

                            InvestmentArrayVO investmentArrayVO = buildInvestmentArrayVO(trxEffDate, currentInvestment.getInvestmentPK(), cumUnits, unitValue);

                            investmentArrays.add(mapInvestmentArrayVOAsElement(investmentArrayVO));

                            priorUVEffDate = trxEffDate;

                            priorCumUnits = cumUnits;
                        }
                    }
                    else
                    {
                        if (trxEffDate.before(businessDate))
                        {
                            Set bucketHistories = currentInvestmentHistory.getEDITTrxHistory().getBucketHistories();

                            cumUnits = getCumUnitsForInvestmentArray(bucketHistories);

                            InvestmentArrayVO investmentArrayVO = buildInvestmentArrayVO(trxEffDate, currentInvestment.getInvestmentPK(), cumUnits, unitValue);

                            investmentArrays.add(mapInvestmentArrayVOAsElement(investmentArrayVO));

                            priorUVEffDate = trxEffDate;

                            priorCumUnits = cumUnits;
                        }
                    }

                    if (trxEffDate.equals(businessDate))
                    {
                        exactMatchFound = true;

                        if (priorUVEffDate != null)
                        {
                            EDITDate nextUVEffDate = priorUVEffDate;

                            int noDaysToGenerate = trxEffDate.getElapsedDays(priorUVEffDate) - 1;

                            if (noDaysToGenerate > 0)
                            {
                                for (int j = 0; j < noDaysToGenerate; j++)
                                {
                                    nextUVEffDate.addDays(1);

                                    InvestmentArrayVO nonBizDayInvArrayVO = buildInvestmentArrayVO(nextUVEffDate, currentInvestment.getInvestmentPK(), priorCumUnits, unitValue);

                                    investmentArrays.add(mapInvestmentArrayVOAsElement(nonBizDayInvArrayVO));
                                }
                            }
                        }

                        Set bucketHistories = currentInvestmentHistory.getEDITTrxHistory().getBucketHistories();

                        cumUnits = getCumUnitsForInvestmentArray(bucketHistories);

                        InvestmentArrayVO investmentArrayVO = buildInvestmentArrayVO(businessDate, currentInvestment.getInvestmentPK(), cumUnits, unitValue);

                        investmentArrays.add(mapInvestmentArrayVOAsElement(investmentArrayVO));

                        priorUVEffDate = businessDate;

                        priorCumUnits = cumUnits;
                    }
                }

                if (!exactMatchFound)
                {
                    InvestmentArrayVO investmentArrayVO = buildInvestmentArrayVO(businessDate, currentInvestment.getInvestmentPK(), priorCumUnits, unitValue);

                    investmentArrays.add(mapInvestmentArrayVOAsElement(investmentArrayVO));

                    priorUVEffDate = businessDate;
                }
            }
        }

        return investmentArrays;
    }

    /**
     * Util method to
     * @param unitValueDate
     * @param investmentFK
     * @param cumUnits
     * @param unitValue
     * @return
     */
    private static InvestmentArrayVO buildInvestmentArrayVO(EDITDate unitValueDate, Long investmentFK, EDITBigDecimal cumUnits, EDITBigDecimal unitValue)
    {
        InvestmentArrayVO investmentArrayVO = new InvestmentArrayVO();

        investmentArrayVO.setUnitValueDate(unitValueDate.getFormattedDate());

        investmentArrayVO.setInvestmentFK(investmentFK.longValue());

        investmentArrayVO.setCumUnits(cumUnits.getBigDecimal());

        investmentArrayVO.setUnitValue(unitValue.getBigDecimal());

        return investmentArrayVO;
    }

    /**
     * Calculates the effective date of the prior MF transaction
     * @param currMFEffDate
     * @return
     */
    private static EDITDate getPriorMFEffDate(EDITDate currMFEffDate)
    {
        EDITDate tempMFEffDate = currMFEffDate.subtractMonths(1);

        EDITDate priorMFEffDate = tempMFEffDate.getEndOfMonthDate();

        BusinessCalendar businessCalendar = new BusinessCalendar();

        BusinessDay businessDay = businessCalendar.getBestBusinessDay(priorMFEffDate);

        priorMFEffDate = businessDay.getBusinessDate();

        return priorMFEffDate;
    }

    /**
     * Sums up the cumUnits for all of the given bucketHistory records
     * @param bucketHistories
     * @return
     */
    private static EDITBigDecimal getCumUnitsForInvestmentArray(Set bucketHistories)
    {
        EDITBigDecimal cumUnits = new EDITBigDecimal();

        for (Iterator iterator = bucketHistories.iterator(); iterator.hasNext();)
        {
            BucketHistory bucketHistory = (BucketHistory) iterator.next();

            cumUnits = cumUnits.addEditBigDecimal(bucketHistory.getCumUnits());
        }

        return cumUnits;
    }

    /**
     * Converts InvestmentArrayVO to DOM Element.
     * @param investmentArrayVO
     */
    private static Element mapInvestmentArrayVOAsElement(InvestmentArrayVO investmentArrayVO)
    {
        Element investmentArrayElement = new DefaultElement("InvestmentArrayVO");

        Element cumUnits = new DefaultElement("CumUnits");
        cumUnits.setText(investmentArrayVO.getCumUnits().toString());

        Element unitValue = new DefaultElement("UnitValue");
        unitValue.setText(investmentArrayVO.getUnitValue().toString());

        Element unitValueDate = new DefaultElement("UnitValueDate");
        unitValueDate.setText(investmentArrayVO.getUnitValueDate());

        Element investmentFK = new DefaultElement("InvestmentFK");
        investmentFK.setText(String.valueOf(investmentArrayVO.getInvestmentFK()));

        investmentArrayElement.add(cumUnits);
        investmentArrayElement.add(unitValue);
        investmentArrayElement.add(unitValueDate);
        investmentArrayElement.add(investmentFK);

        return investmentArrayElement;
    }

    /**
     * From the segmentPK build document elements with the segment and it children of Payout,
     * Life, Investments, InvestmentAllocations and Buckets.
     * @param segmentPK, key of selected contract
     * @return
     */
    public static PRASEDocResult buildSegmentFromSegmentPK(Long segmentPK)
    {                
       String hql = "select segment" +
                " from Segment segment " +
                " left join fetch segment.Investments investment" +
                " left join fetch investment.InvestmentAllocations" +
                " left join fetch investment.Buckets bucket" +
                " left join fetch bucket.AnnualizedSubBuckets" +
                " left join fetch segment.Payouts" +
                " left join fetch segment.Lifes" +
                " where segment.SegmentPK = :segmentPK";

        Map params = new HashMap();

        params.put("segmentPK", segmentPK);

        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);

        // Segment
        Segment segment = (Segment) results.get(0);

        Element segmentElement = SessionHelper.mapToElement(segment, SessionHelper.EDITSOLUTIONS, false, false);

        // Payout
        Payout payout = segment.getPayout();

        if (payout != null)
        {
            Element payoutElement = SessionHelper.mapToElement(payout, SessionHelper.EDITSOLUTIONS, false, false);

            segmentElement.add(payoutElement);
        }

        // Life
        Life life = segment.getLife();

        if (life != null)
        {
            Element lifeElement = SessionHelper.mapToElement(life, SessionHelper.EDITSOLUTIONS, false, false);

            segmentElement.add(lifeElement);
        }

        // Investment
        Set investments = segment.getInvestments();

        for (Iterator iterator = investments.iterator(); iterator.hasNext();)
        {
            Investment investment = (Investment) iterator.next();

            Element investmentElement = SessionHelper.mapToElement(investment, SessionHelper.EDITSOLUTIONS, false, false);

            segmentElement.add(investmentElement);

            // InvestmentAllocation
            InvestmentAllocation investmentAllocation = investment.getInvestmentAllocation();

            if (investmentAllocation != null)
            {
                Element investmentAllocationElement = SessionHelper.mapToElement(investmentAllocation, SessionHelper.EDITSOLUTIONS, false, false);

                investmentElement.add(investmentAllocationElement);
            }

            // Bucket
            Set buckets = investment.getBuckets();

            for (Iterator iterator1 = buckets.iterator(); iterator1.hasNext();)
            {
                Bucket bucket = (Bucket) iterator1.next();

                Element bucketElement = SessionHelper.mapToElement(bucket, SessionHelper.EDITSOLUTIONS, false, false);

                investmentElement.add(bucketElement);

                // AnnualizedSubBucket
                Set annualizedSubBuckets = bucket.getAnnualizedSubBuckets();

                for (Iterator iterator2 = annualizedSubBuckets.iterator(); iterator2.hasNext();)
                {
                    AnnualizedSubBucket annualizedSubBucket = (AnnualizedSubBucket) iterator2.next();

                    Element annualizedSubBucketElement = SessionHelper.mapToElement(annualizedSubBucket, SessionHelper.EDITSOLUTIONS, false, false);

                    bucketElement.add(annualizedSubBucketElement);
                }
            }
        }

        PRASEDocResult praseDocResult = new PRASEDocResult(segment, segmentElement);

        return praseDocResult;
    }
}
