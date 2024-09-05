/*
 * User: sdorman
 * Date: Jun 2, 2004
 * Time: 12:36:15 PM
 * 
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package extension;

import fission.utility.Util;
import electric.registry.Registry;
import electric.proxy.IProxy;
import edit.common.vo.*;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;

import java.util.*;




public class PerfTuningClient
{
    final static boolean debug = false;    // for debug writes to screen

    public static void main(String[] args) throws Exception, Throwable
    {
        String url = "http://localhost:8080/PORTAL/services/performanceTuningAdapter.wsdl";

        IProxy proxy = Registry.bind(url);


        PerfTuningClient soapClient = new PerfTuningClient();

//        soapClient.populateCompanyStructure(proxy);

        soapClient.populateVarLife(proxy);

        soapClient.populateEIA(proxy);
    }



    private String addClient(IProxy proxy, String clientId, String birthDate, String genderCT,
                             String firstName, String lastName) throws Throwable
    {
        ClientDetailVO clientDetailVO = new ClientDetailVO();
        clientDetailVO.setClientIdentification(clientId);
        clientDetailVO.setTaxIdentification(clientId);
        clientDetailVO.setTrustTypeCT("Individual");
        clientDetailVO.setBirthDate(birthDate);
        clientDetailVO.setGenderCT(genderCT);
        clientDetailVO.setFirstName(firstName);
        clientDetailVO.setLastName(lastName);
        clientDetailVO.setStatusCT("Active");

        TaxInformationVO taxInformationVO = new TaxInformationVO();
        taxInformationVO.setTaxIdTypeCT("SocialSecurityNumber");

        ClientAddressVO clientAddressVO = new ClientAddressVO();
        clientAddressVO.setAddressTypeCT("Primary");
        clientAddressVO.setAddressLine1(lastName);
        clientAddressVO.setCity("Glastonbury");
        clientAddressVO.setStateCT("CT");
        clientAddressVO.setZipCode("06033");
        clientAddressVO.setEffectiveDate("2004/01/01");
        clientAddressVO.setTerminationDate("9999/12/31");

        PreferenceVO preferenceVO = new PreferenceVO();
        preferenceVO.setDisbursementSourceCT("Check");
        preferenceVO.setOverrideStatus("P");

        clientDetailVO.addClientAddressVO(clientAddressVO);
        clientDetailVO.addPreferenceVO(preferenceVO);
        clientDetailVO.addTaxInformationVO(taxInformationVO);


        String clientDetailAsXML = Util.marshalVO(clientDetailVO);

        String clientDetailPK = (String) proxy.invoke("addClient", new Object[] {clientDetailAsXML});

        if (debug)
        {
            System.out.println("SOAPClient.addClient");

            if (clientDetailPK != null)
            {
                System.out.println("clientDetailPK = " + clientDetailPK);
            }
            else
            {
                System.out.println("No Results");
            }
        }

        return clientDetailPK;
    }

    private String  addClientRole(IProxy proxy, String clientId, String roleTypeCT) throws Throwable
    {
        String clientIdentification = clientId;

        ClientRoleVO clientRoleVO = new ClientRoleVO();

        clientRoleVO.setRoleTypeCT(roleTypeCT);
        clientRoleVO.setOverrideStatus("P");

        String clientRoleAsXML = Util.marshalVO(clientRoleVO);

        String clientRolePK = (String) proxy.invoke("addClientRole", new Object[] {clientIdentification, clientRoleAsXML});

        if (debug)
        {
            System.out.println("SOAPClient.addClientRole");

            if (clientRolePK != null)
            {
                System.out.println("clientRolePK = " + clientRolePK);
            }
            else
            {
                System.out.println("No Results");
            }
        }

        return clientRolePK;
    }

    private String  addContractClientToExistingContractAndClientRole(IProxy proxy, String clientRolePK,
                            String contractNumber, String effectiveDate) throws Throwable
    {
        ContractClientVO contractClientVO = buildContractClientVO(clientRolePK, effectiveDate, EDITDate.DEFAULT_MAX_DATE, "OWN");

        InduaseContractClientVO induaseContractClientVO = new InduaseContractClientVO();
        induaseContractClientVO.setContractClientVO(contractClientVO);

        String induaseContractClientAsXML = Util.marshalVO(induaseContractClientVO);

        String contractClientPK = (String) proxy.invoke("addContractClientToExistingContractAndClientRole",
                new Object[] {contractNumber, induaseContractClientAsXML});


        if (debug)
        {
            System.out.println("SOAPClient.addClientRoleToContract");

            if (contractClientPK != null)
            {
                System.out.println("contractClientPK = " + contractClientPK);
            }
            else
            {
                System.out.println("No Results");
            }
        }

        return contractClientPK;
    }


    private String addSuspense(IProxy proxy, String userDefNumber, String suspenseAmount, String effectiveDate) throws Throwable
    {
        SuspenseVO suspenseVO = new SuspenseVO();
        suspenseVO.setUserDefNumber(userDefNumber);
        suspenseVO.setEffectiveDate(effectiveDate);
        suspenseVO.setSuspenseAmount(new EDITBigDecimal(suspenseAmount).getBigDecimal());
        suspenseVO.setDirectionCT("Apply");
        suspenseVO.setSuspenseType("Contract");
        suspenseVO.setOriginalAmount(new EDITBigDecimal(suspenseAmount).getBigDecimal());
        suspenseVO.setAccountingPendingInd("Y");
        suspenseVO.setMaintenanceInd("M");

        String suspenseAsXML = Util.marshalVO(suspenseVO);

        String suspensePK = (String) proxy.invoke("addSuspense", new Object[] {suspenseAsXML});


        if (debug)
        {
            System.out.println("SOAPClient.addSuspense");

            if (suspensePK != null)
            {
                System.out.println("suspensePK = " + suspensePK);
            }
            else
            {
                System.out.println("No Results");
            }
        }

        return suspensePK;
    }

    private SegmentVO addPolicyLife(IProxy proxy, String contractNumber, String effectiveDate,
                             ProductStructureVO companyStructureVO, ArrayList clientRolePKs) throws Throwable
    {
//        String masterNumber = contractNumber;
        String caseNumber = " ";

        SegmentVO segmentVO = new SegmentVO();
        segmentVO.setContractNumber(contractNumber);
        segmentVO.setEffectiveDate(effectiveDate);
        segmentVO.setIssueDate(effectiveDate);
        segmentVO.setAmount(new EDITBigDecimal("0").getBigDecimal());
        segmentVO.setSegmentNameCT("Life");
        segmentVO.setOptionCodeCT("VL");
        segmentVO.setSegmentStatusCT("Pending");
        segmentVO.setIssueStateCT("CT");
        segmentVO.setLastAnniversaryDate(effectiveDate);
        segmentVO.setApplicationSignedDate(effectiveDate);
        segmentVO.setApplicationReceivedDate(effectiveDate);
        segmentVO.setWaiverInEffect("N");
        segmentVO.setCreationDate(effectiveDate);
        segmentVO.setCreationOperator("System");

        //  Add Life
        LifeVO lifeVO = new LifeVO();
        lifeVO.setDeathBenefitOptionCT("Level");
        lifeVO.setFaceAmount(new EDITBigDecimal("1000000", 2).getBigDecimal());
        lifeVO.setOption7702CT("Guideline");

        PayoutVO payoutVO = new PayoutVO();
        payoutVO.setCertainPeriodEndDate("9999/12/31");
        payoutVO.setFinalPaymentDate("9999/12/31");
        payoutVO.setPaymentStartDate("9999/12/31");
        payoutVO.setNextPaymentDate("9999/12/31");

        segmentVO.addPayoutVO(payoutVO);
        segmentVO.addLifeVO(lifeVO);

        //  Add Investments
        InvestmentVO investmentVO1 = buildInvestmentVO(".3", "1083613932106");
        InvestmentVO investmentVO2 = buildInvestmentVO(".7", "1082752948622");
        
        segmentVO.addInvestmentVO(investmentVO1);
        segmentVO.addInvestmentVO(investmentVO2);
        
        //  Add ContractClients

        // OWNER
        ContractClientVO contractClientVOOwner = buildContractClientVO((String) clientRolePKs.get(0), effectiveDate, EDITDate.DEFAULT_MAX_DATE, "OWN");

        // INSURED
        ContractClientVO contractClientVOInsured = buildContractClientVO((String) clientRolePKs.get(1), effectiveDate, EDITDate.DEFAULT_MAX_DATE, "Insured");

        
        InduaseContractClientVO induaseContractClientVOOwner = new InduaseContractClientVO();
        induaseContractClientVOOwner.setContractClientVO(contractClientVOOwner);

        InduaseContractClientVO induaseContractClientVOInsured = new InduaseContractClientVO();
        induaseContractClientVOInsured.setContractClientVO(contractClientVOInsured);

        InduaseContractClientVO[] induaseContractClientVOs = {induaseContractClientVOOwner, induaseContractClientVOInsured};

        
        
        String segmentAsXML = Util.marshalVO(segmentVO);
        String companyStructureAsXML = Util.marshalVO(companyStructureVO);
        String[] induaseContractClientsAsXML = Util.marshalVOs(induaseContractClientVOs);

        String segmentPK = (String) proxy.invoke("addPolicy", new Object[] {segmentAsXML, caseNumber,
                                 companyStructureAsXML,
                                 induaseContractClientsAsXML});


        if (debug)
        {
            System.out.println("SOAPClient.addPolicy");

            if (segmentPK != null)
            {
                System.out.println("segmentPK = " + segmentPK);
            }
            else
            {
                System.out.println("No Results");
            }
        }

        return segmentVO;
    }

    private void deleteClient(IProxy proxy, String clientId) throws Throwable
    {
        proxy.invoke("deleteClient", new Object[] {clientId});

        if (debug)
        {
            System.out.println("SOAPClient.deleteClient");
        }
    }

    private void deleteSuspense(IProxy proxy, String suspensePK) throws Throwable
    {
        proxy.invoke("deleteSuspense", new Object[] {suspensePK});

        if (debug)
        {
            System.out.println("SOAPClient.deleteSuspense");
        }
    }

    private void deleteTransaction(IProxy proxy, String editTrxPK) throws Throwable
    {
        proxy.invoke("deleteTransaction", new Object[] {editTrxPK});

        if (debug)
        {
            System.out.println("SOAPClient.deleteTransaction");
        }
    }

    private void updateClient(IProxy proxy, String clientId, String birthDate, String genderCT) throws Throwable
    {
        ClientDetailVO clientDetailVO = new ClientDetailVO();
        clientDetailVO.setClientIdentification(clientId);
        clientDetailVO.setBirthDate(birthDate);
        clientDetailVO.setGenderCT(genderCT);

        String clientDetailAsXML = Util.marshalVO(clientDetailVO);

        proxy.invoke("updateClient", new Object[] {clientDetailAsXML});

        if (debug)
        {
            System.out.println("SOAPClient.updateClient");
        }
    }

    private void updateSuspense(IProxy proxy, String suspensePK, String userDefNumber, String effectiveDate, 
                                String suspenseAmount, String originalContractNumber, String originalAmount) throws Throwable
    {
//        long suspensePK = 1091794526681L;

        SuspenseVO suspenseVO = new SuspenseVO();
        suspenseVO.setSuspensePK(new Long(suspensePK).longValue());
        suspenseVO.setUserDefNumber(userDefNumber);
        suspenseVO.setEffectiveDate(effectiveDate);
        suspenseVO.setSuspenseAmount(new EDITBigDecimal(suspenseAmount).getBigDecimal());
        suspenseVO.setMemoCode(null);
        suspenseVO.setOriginalContractNumber(originalContractNumber);
        suspenseVO.setOriginalAmount(new EDITBigDecimal(originalAmount).getBigDecimal());
        suspenseVO.setOriginalMemoCode(null);
        suspenseVO.setPendingSuspenseAmount(new EDITBigDecimal("0").getBigDecimal());
        suspenseVO.setAccountingPendingInd("Y");
        suspenseVO.setMaintenanceInd("M");
        suspenseVO.setOperator("System");
//        suspenseVO.setMaintDateTime("2004/08/06 12:14:09:015");
        suspenseVO.setProcessDate("2004/08/06");
        suspenseVO.setDirectionCT("Apply");
        suspenseVO.setPremiumTypeCT("Cash");
        suspenseVO.setSuspenseType("Contract");
        suspenseVO.setStatus("N");
        suspenseVO.setTaxYear(2004);


        String suspenseAsXML = Util.marshalVO(suspenseVO);

        proxy.invoke("updateSuspense", new Object[] {suspenseAsXML});

        if (debug)
        {
            System.out.println("SOAPClient.updateSuspense");
        }
    }

    private void updatePolicy(IProxy proxy, String contractNumber, String effectiveDate, String amount, 
                              String segmentNameCT, String optionCodeCT, String segmentStatusCT, String issueStateCT) throws Throwable
    {
        SegmentVO segmentVO = new SegmentVO();
        segmentVO.setContractNumber(contractNumber);
        segmentVO.setEffectiveDate(effectiveDate);
//        segmentVO.setApplicationDate("2004/06/18");
        segmentVO.setAmount(new EDITBigDecimal(amount).getBigDecimal());
        segmentVO.setSegmentNameCT(segmentNameCT);
        segmentVO.setOptionCodeCT(optionCodeCT);
        segmentVO.setSegmentStatusCT(segmentStatusCT);
        segmentVO.setIssueStateCT(issueStateCT);

        String segmentAsXML = Util.marshalVO(segmentVO);

        proxy.invoke("updatePolicy", new Object[] {segmentAsXML});

        if (debug)
        {
            System.out.println("SOAPClient.updatePolicy");
        }
    }

    private void updateTransaction(IProxy proxy, String contractNumber, String groupSetupPK, String groupAmount, String employeeContribution,
                                   String employerContribution, String contractSetupPK, String policyAmount, String costBasis,
                                   String amountReceived, String outSuspensePK, String suspenseFK, String outSuspenseAmount,
                                   String editTrxPK, String editTrxEffectiveDate, String editTrxTrxAmount) throws Throwable
    {
        GroupSetupVO groupSetupVO = new GroupSetupVO();
        groupSetupVO.setGroupSetupPK(new Long(groupSetupPK).longValue());
        groupSetupVO.setPremiumTypeCT("Cash");
        groupSetupVO.setGroupAmount(new EDITBigDecimal(groupAmount).getBigDecimal());
        groupSetupVO.setEmployeeContribution(new EDITBigDecimal(employeeContribution).getBigDecimal());
        groupSetupVO.setEmployerContribution(new EDITBigDecimal(employerContribution).getBigDecimal());

        ContractSetupVO contractSetupVO = new ContractSetupVO();
        contractSetupVO.setContractSetupPK(new Long(contractSetupPK).longValue());
        contractSetupVO.setPolicyAmount(new EDITBigDecimal(policyAmount).getBigDecimal());
        contractSetupVO.setCostBasis(new EDITBigDecimal(costBasis).getBigDecimal());
        contractSetupVO.setAmountReceived(new EDITBigDecimal(amountReceived).getBigDecimal());

        OutSuspenseVO outSuspenseVO = new OutSuspenseVO();
        outSuspenseVO.setOutSuspensePK(new Long(outSuspensePK).longValue());
        outSuspenseVO.setSuspenseFK(new Long(suspenseFK).longValue());
        outSuspenseVO.setAmount(new EDITBigDecimal(outSuspenseAmount).getBigDecimal());

        EDITTrxVO editTrxVO = new EDITTrxVO();
        editTrxVO.setEDITTrxPK(new Long(editTrxPK).longValue());
        editTrxVO.setEffectiveDate(editTrxEffectiveDate);  // don't make older than date it was issued because it will undo the issue trx
        editTrxVO.setPendingStatus("P");
        editTrxVO.setSequenceNumber(1);
        editTrxVO.setTaxYear(2004);
        editTrxVO.setTrxAmount(new EDITBigDecimal(editTrxTrxAmount).getBigDecimal());
//        editTrxVO.setDueDate();   // only if have scheduledEvent
        editTrxVO.setTransactionTypeCT("PY");
        editTrxVO.setTrxIsRescheduledInd("N");
        editTrxVO.setNoAccountingInd("N");
        editTrxVO.setNoCommissionInd("N");
        editTrxVO.setZeroLoadInd("N");
        editTrxVO.setNoCorrespondenceInd("N");

        contractSetupVO.addOutSuspenseVO(outSuspenseVO);
        groupSetupVO.addContractSetupVO(contractSetupVO);

        String groupSetupAsXML = Util.marshalVO(groupSetupVO);
        String editTrxAsXML = Util.marshalVO(editTrxVO);

        proxy.invoke("updateTransaction", new Object[] {contractNumber, groupSetupAsXML, editTrxAsXML});

        if (debug)
        {
            System.out.println("SOAPClient.updateTransaction");
        }
    }

//    private void saveGroupMaster(IProxy proxy, String masterNumber, String statusInd, String effectiveDate,
//                                 String methodCT, String modeCT, String billDay, String lastDayOfMonth) throws Throwable
//    {
//        MasterVO masterVO = new MasterVO();
//        masterVO.setMasterNumber(masterNumber);
//        masterVO.setStatusInd(statusInd); // have no idea what this is
//        masterVO.setEffectiveDate(effectiveDate);
//
//        BillingVO billingVO = new BillingVO();
//        billingVO.setMethodCT(methodCT);
//        billingVO.setModeCT(modeCT);
//        billingVO.setBillDay(new Integer(billDay).intValue());
//        billingVO.setLastDayOfMonth(lastDayOfMonth);
//
//        String masterAsXML = Util.marshalVO(masterVO);
//        String billingAsXML = Util.marshalVO(billingVO);
//
//        proxy.invoke("saveGroupMaster", new Object[] {masterAsXML, billingAsXML});
//
//        if (debug)
//        {
//            System.out.println("SOAPClient.saveGroupMaster");
//        }
//    }
//
//    private void saveListMaster(IProxy proxy, String masterNumber, String statusInd, String effectiveDate) throws Throwable
//    {
//        MasterVO masterVO = new MasterVO();
//        masterVO.setMasterNumber(masterNumber);
//        masterVO.setStatusInd(statusInd); // have no idea what this is
//        masterVO.setEffectiveDate(effectiveDate);
//
//        String masterAsXML = Util.marshalVO(masterVO);
//
//        proxy.invoke("saveListMaster", new Object[] {masterAsXML});
//
//        if (debug)
//        {
//            System.out.println("SOAPClient.saveListMaster");
//        }
//    }

    private void issueNewBusiness(IProxy proxy, String contractNumber, String issueDate, String operator, 
                                  String lastDayOfMonthInd) throws Throwable
    {
//        String contractNumber = "suzanne1a";
//        String issueDate = "2004/08/13";
//        String operator = "robk";
//        String lastDayOfMonthInd = "N";

        if (debug)
        {
            System.out.println("SOAPClient.issueNewBusiness: invoking issueNewBusiness");
        }

        proxy.invoke("issueNewBusiness", new Object[] {contractNumber, issueDate, operator, lastDayOfMonthInd});

        if (debug)
        {
            System.out.println("SOAPClient.issueNewBusiness");
        }
    }

    private void processInforceQuote(IProxy proxy, String contractNumber, String quoteDate) throws Throwable
    {
//        String contractNumber = "RK0804a";
//        String quoteDate = "2004/08/03";        // must be >= effective date of Segment


        String quoteAsXML = (String) proxy.invoke("processInforceQuote", new Object[] {contractNumber, quoteDate});

        QuoteVO quoteVO = (QuoteVO) Util.unmarshalVO(QuoteVO.class, quoteAsXML);

        if (debug)
        {
            System.out.println("SOAPClient.processInforceQuote");

            if (quoteVO != null)
            {
                System.out.println("quoteVO.getQuoteDate() = " + quoteVO.getQuoteDate());
                System.out.println("quoteVO.getAccumValue() = " + quoteVO.getAccumValue());
            }
            else
            {
                System.out.println("No Results");
            }
        }
    }

    private void processTransaction(IProxy proxy, String contractNumber) throws Throwable
    {
        GroupSetupVO groupSetupVO = new GroupSetupVO();
        groupSetupVO.setPremiumTypeCT("Cash");
        groupSetupVO.setGroupAmount(new EDITBigDecimal("500.00").getBigDecimal());
        groupSetupVO.setEmployeeContribution(new EDITBigDecimal("0").getBigDecimal());
        groupSetupVO.setEmployerContribution(new EDITBigDecimal("0").getBigDecimal());

        ContractSetupVO contractSetupVO = new ContractSetupVO();
        contractSetupVO.setPolicyAmount(new EDITBigDecimal("500.0").getBigDecimal());
        contractSetupVO.setCostBasis(new EDITBigDecimal("0").getBigDecimal());
        contractSetupVO.setAmountReceived(new EDITBigDecimal("0").getBigDecimal());

        OutSuspenseVO outSuspenseVO = new OutSuspenseVO();
        outSuspenseVO.setSuspenseFK(1091794526448L);
        outSuspenseVO.setAmount(new EDITBigDecimal("500.00").getBigDecimal());

//        ClientSetupVO clientSetupVO = new ClientSetupVO();
//        clientSetupVO.setContractClientFK();
//        clientSetupVO.setClientRoleFK();

        EDITTrxVO editTrxVO = new EDITTrxVO();
        editTrxVO.setEffectiveDate("2004/07/01");  // don't make older than date it was issued because it will undo the issue trx
        editTrxVO.setPendingStatus("P");
        editTrxVO.setSequenceNumber(1);
        editTrxVO.setTaxYear(2004);
        editTrxVO.setTrxAmount(new EDITBigDecimal("500.00").getBigDecimal());
//        editTrxVO.setDueDate();   // only if have scheduledEvent
        editTrxVO.setTransactionTypeCT("PY");
        editTrxVO.setTrxIsRescheduledInd("N");
        editTrxVO.setNoAccountingInd("N");
        editTrxVO.setNoCommissionInd("N");
        editTrxVO.setZeroLoadInd("N");
        editTrxVO.setNoCorrespondenceInd("N");

        contractSetupVO.addOutSuspenseVO(outSuspenseVO);
//        contractSetupVO.addClientSetupVO(clientSetupVO);
        groupSetupVO.addContractSetupVO(contractSetupVO);

        String groupSetupAsXML = Util.marshalVO(groupSetupVO);
        String editTrxAsXML = Util.marshalVO(editTrxVO);

        proxy.invoke("processTransaction", new Object[] {contractNumber, groupSetupAsXML, editTrxAsXML});

        if (debug)
        {
            System.out.println("SOAPClient.processTransaction");
        }
    }

    private void reverseHistory(IProxy proxy, String contractNumber, String historyPK, String historyType) throws Throwable
    {
//        String contractNumber = "suzanne1a";
//        String historyPK = "1092763266778";
//        String historyType = "ChangeHistory";

        proxy.invoke("reverseHistory", new Object[] {contractNumber, historyPK, historyType});

        if (debug)
        {
            System.out.println("SOAPClient.reverseHistory");
        }
    }

    private void processPremiumLife(IProxy proxy, SegmentVO segmentVO, int numberOfContracts) throws Throwable
    {
        //Now setup and process the initial premium trx
        String policyAmount = null;

        if (numberOfContracts <= 20000)
        {
            policyAmount = "10000";
        }
        else if (numberOfContracts > 20000 && numberOfContracts <= 30000)
        {
            policyAmount = "15000";
        }
        else if (numberOfContracts > 30000 && numberOfContracts <= 40000)
        {
            policyAmount = "20000";
        }
        else if (numberOfContracts > 40000 && numberOfContracts <= 50000)
        {
            policyAmount = "25000";
        }

        String editTrxPK = (String)proxy.invoke("addPremiumTrx", new Object[] {segmentVO.getContractNumber(), policyAmount, segmentVO.getEffectiveDate()});

        if (debug)
        {
            System.out.println("SOAPClient.addPremiumTrx");

            if (editTrxPK == null)
            {
//            System.out.println("editTrxPK = " + editTrxPK);
//        }
//        else
//        {
                System.out.println("No Results");
            }
        }
    }

    private void processEIATransactions(IProxy proxy, SegmentVO segmentVO, int numberOfContracts) throws Throwable
     {
         //Now setup and process the initial premium trx
         String contractNumber = segmentVO.getContractNumber();
         processInitialPremium(proxy, contractNumber, numberOfContracts, segmentVO.getEffectiveDate());

         //Subsequent premium for some EIA08 contracts
         if (contractNumber.startsWith("EIA08") && numberOfContracts <= 3000)
         {
             String policyAmount = "5000";
             String editTrxPK = (String)proxy.invoke("addPremiumTrx", new Object[] {contractNumber, policyAmount, "2005/08/01"});

         }

         else if (contractNumber.startsWith("SUR"))
         {
             String policyAmount = "5000";
             String editTrxPK = (String)proxy.invoke("addSurrenderTrx", new Object[] {contractNumber, policyAmount, "2005/08/01"});
         }
         else if (contractNumber.startsWith("TRAN"))
         {
             String policyAmount = "5000";
             String editTrxPK = (String)proxy.invoke("addTransferTrx", new Object[] {contractNumber, policyAmount, "2005/08/01"});
         }
         else if (contractNumber.startsWith("WD"))
         {
             String policyAmount = "5000";
             String editTrxPK = (String)proxy.invoke("addWithdrawalTrx", new Object[] {contractNumber, policyAmount, "2005/08/01"});
         }
         else if (contractNumber.startsWith("DTH"))
         {
             String policyAmount = "5000";
             String editTrxPK = (String)proxy.invoke("addDeathTrx", new Object[] {contractNumber, policyAmount, "2005/08/01"});
         }

         if (debug)
         {
             System.out.println("SOAPClient.endEIATrx");
         }

     }

    private void processInitialPremium(IProxy proxy, String contractNumber, int numberOfContracts, String effectiveDate) throws Throwable
    {
        String policyAmount = null;
        if (numberOfContracts <= 1000)
        {
            policyAmount = "10000";
        }
        else if (numberOfContracts > 1000 && numberOfContracts <= 2000)
        {
            policyAmount = "20000";
        }
        else if (numberOfContracts > 2000 && numberOfContracts <= 3000)
        {
            policyAmount = "30000";
        }
        else if (numberOfContracts > 3000 && numberOfContracts <= 5000)
        {
            policyAmount = "40000";
        }
        else if (numberOfContracts > 5000 && numberOfContracts <= 30000)
        {
            policyAmount = "50000";
        }
        else if (numberOfContracts > 30000 && numberOfContracts <= 50000)
        {
            policyAmount = "60000";
        }
        else if (numberOfContracts > 50000)
        {
            policyAmount = "75000";
        }

        String editTrxPK = (String)proxy.invoke("addPremiumTrx", new Object[] {contractNumber, policyAmount, effectiveDate});
    }

//    private void retrieveAllMastersOfGroupType(IProxy proxy, String groupTypeCT) throws Throwable
//    {
////        String groupTypeCT = "INDIVIDUAL";
//
//        String[] masterVOsAsXML = (String[]) proxy.invoke("retrieveAllMastersOfGroupType", new Object[] {groupTypeCT});
//
//        MasterVO[] masterVOs = (MasterVO[]) Util.unmarshalVOs(MasterVO.class, masterVOsAsXML);
//
//        if (debug)
//        {
//            System.out.println("SOAPClient.retrieveAllMastersOfGroupType");
//
//            if (masterVOs != null)
//            {
//                System.out.println("masterVOs.length = " + masterVOs.length);
//
//                for (int i =0; i < masterVOs.length; i++)
//                {
//                    System.out.println("i = " + i);
//                    System.out.println("masterVOs[i].getMasterNumber() = " + masterVOs[i].getMasterNumber());
//                    System.out.println("masterVOs[i].getGroupTypeCT() = " + masterVOs[i].getGroupTypeCT());
//                }
//            }
//        }
//    }

    private void retrieveAllPendingTransactions(IProxy proxy) throws Throwable
    {
        String[] editTrxVOsAsXML = (String[]) proxy.invoke("retrieveAllPendingTransactions", new Object[] {});

        EDITTrxVO[] editTrxVOs = (EDITTrxVO[]) Util.unmarshalVOs(EDITTrxVO.class, editTrxVOsAsXML);

        if (debug)
        {
            System.out.println("SOAPClient.retrieveAllPendingTransactions");

            if (editTrxVOs != null)
            {
                System.out.println("editTrxVOs.length = " + editTrxVOs.length);

                for (int i = 0; i < editTrxVOs.length; i++)
                {
                    System.out.println("i = " + i);
                    System.out.println("editTrxVOs[i].getEDITTrxPK() = " + editTrxVOs[i].getEDITTrxPK());
                    System.out.println("editTrxVOs[i].getEffectiveDate() = " + editTrxVOs[i].getEffectiveDate());
                    System.out.println("editTrxVOs[i].getTransactionTypeCT() = " + editTrxVOs[i].getTransactionTypeCT());
                }
            }
            else
            {
                System.out.println("No Results");
            }
        }
    }

    private void retrieveAllPendingTransactionsForContract(IProxy proxy, String contractNumber) throws Throwable
    {
        String[] editTrxVOsAsXML = (String[]) proxy.invoke("retrieveAllPendingTransactionsForContract", new Object[] {contractNumber});

        EDITTrxVO[] editTrxVOs = (EDITTrxVO[]) Util.unmarshalVOs(EDITTrxVO.class, editTrxVOsAsXML);

        if (debug)
        {
            System.out.println("SOAPClient.retrieveAllPendingTransactionsForContract");

            if (editTrxVOs != null)
            {
                System.out.println("editTrxVOs.length = " + editTrxVOs.length);

                for (int i = 0; i < editTrxVOs.length; i++)
                {
                    System.out.println("i = " + i);
                    System.out.println("editTrxVOs[i].getEDITTrxPK() = " + editTrxVOs[i].getEDITTrxPK());
                    System.out.println("editTrxVOs[i].getEffectiveDate() = " + editTrxVOs[i].getEffectiveDate());
                    System.out.println("editTrxVOs[i].getTransactionTypeCT() = " + editTrxVOs[i].getTransactionTypeCT());
                }
            }
            else
            {
                System.out.println("No Results");
            }
        }
    }

    private void retrieveAllTransactionHistoryForContract(IProxy proxy, String contractNumber) throws Throwable
    {
        String[] editTrxHistoryVOsAsXML = (String[]) proxy.invoke("retrieveAllTransactionHistoryForContract", new Object[] {contractNumber});

        EDITTrxHistoryVO[] editTrxHistoryVOs = (EDITTrxHistoryVO[]) Util.unmarshalVOs(EDITTrxHistoryVO.class, editTrxHistoryVOsAsXML);

        if (debug)
        {
            System.out.println("SOAPClient.retrieveAllTransactionHistoryForContract");

            if (editTrxHistoryVOs != null)
            {
                System.out.println("editTrxHistoryVOs.length = " + editTrxHistoryVOs.length);

                for (int i = 0; i < editTrxHistoryVOs.length; i++)
                {
                    System.out.println("i = " + i);
                    System.out.println("editTrxHistoryVOs[i].getEDITTrxHistoryPK() = " + editTrxHistoryVOs[i].getEDITTrxHistoryPK());
                    System.out.println("editTrxHistoryVOs[i].getEDITTrxFK() = " + editTrxHistoryVOs[i].getEDITTrxFK());
//                System.out.println("editTrxHistoryVOs[i].getProcessDate() = " + editTrxHistoryVOs[i].getProcessDate());
                    System.out.println("editTrxHistoryVOs[i].getCorrespondenceTypeCT() = " + editTrxHistoryVOs[i].getCorrespondenceTypeCT());
                }
            }
            else
            {
                System.out.println("No Results");
            }
        }
    }

    private void retrieveAllChangeHistoryForContract(IProxy proxy, String contractNumber) throws Throwable
    {
        String[] changeHistoryVOsAsXML = (String[]) proxy.invoke("retrieveAllChangeHistoryForContract", new Object[] {contractNumber});

        ChangeHistoryVO[] changeHistoryVOs = (ChangeHistoryVO[]) Util.unmarshalVOs(ChangeHistoryVO.class, changeHistoryVOsAsXML);

        if (debug)
        {
            System.out.println("SOAPClient.retrieveAllChangeHistoryForContract");

            if (changeHistoryVOs != null)
            {
                System.out.println("changeHistoryVOs.length = " + changeHistoryVOs.length);

                for (int i = 0; i < changeHistoryVOs.length; i++)
                {
                    System.out.println("i = " + i);
                    System.out.println("changeHistoryVOs[i].getChangeHistoryPK() = " + changeHistoryVOs[i].getChangeHistoryPK());
                    System.out.println("changeHistoryVOs[i].getEffectiveDate() = " + changeHistoryVOs[i].getEffectiveDate());
                    System.out.println("changeHistoryVOs[i].getTableName() = " + changeHistoryVOs[i].getTableName());
                    System.out.println("changeHistoryVOs[i].getFieldName() = " + changeHistoryVOs[i].getFieldName());
                    System.out.println("changeHistoryVOs[i].getBeforeValue() = " + changeHistoryVOs[i].getBeforeValue());
                    System.out.println("changeHistoryVOs[i].getAfterValue() = " + changeHistoryVOs[i].getAfterValue());
                }
            }
        }
    }



    private void retrieveAllSuspense(IProxy proxy) throws Throwable
    {
        String[] suspenseVOsAsXML = (String[]) proxy.invoke("retrieveAllSuspense", new Object[] {});

        SuspenseVO[] suspenseVOs = (SuspenseVO[]) Util.unmarshalVOs(SuspenseVO.class, suspenseVOsAsXML);

        if (debug)
        {
            System.out.println("SOAPClient.retrieveAllSuspense");

            if (suspenseVOs != null)
            {
                System.out.println("suspenseVOs.length = " + suspenseVOs.length);

                for (int i = 0; i < suspenseVOs.length; i++)
                {
                    System.out.println("i = " + i + ", suspenseVOs[i].getSuspensePK() = " + suspenseVOs[i].getSuspensePK());
                    System.out.println("i = " + i + ", suspenseVOs[i].getSuspenseType() = " + suspenseVOs[i].getSuspenseType());
                    System.out.println("i = " + i + ", suspenseVOs[i].getEffectiveDate() = " + suspenseVOs[i].getEffectiveDate());
                }
            }
            else
            {
                System.out.println("No Results");
            }
        }
    }

    private void retrievePolicy(IProxy proxy, String contractNumber) throws Throwable
    {
        String segmentVOAsXML = (String) proxy.invoke("retrievePolicy", new Object[] {contractNumber});

        SegmentVO segmentVO = (SegmentVO) Util.unmarshalVO(SegmentVO.class, segmentVOAsXML);

        if (debug)
        {
            if (segmentVO != null)
            {
                System.out.println("SOAPClient.retrievePolicy");
                System.out.println("segmentVO.getSegmentPK() = " + segmentVO.getSegmentPK());
                System.out.println("segmentVO.getContractNumber() = " + segmentVO.getContractNumber());
                System.out.println("segmentVO.getSegmentNameCT() = " + segmentVO.getSegmentNameCT());

                System.out.println("segmentVO.getContractClientVOCount() = " + segmentVO.getContractClientVOCount());

                ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();

                if (contractClientVOs != null)
                {
                    for (int i = 0; i < segmentVO.getContractClientVOCount(); i++)
                    {
                        System.out.println("contractClientVOs[i].getClientRoleFK() = " + contractClientVOs[i].getClientRoleFK());
                        System.out.println("contractClientVOs[i].getEffectiveDate() = " + contractClientVOs[i].getEffectiveDate());
                    }
                }
            }
            else
            {
                System.out.println("No Results");
            }
        }
    }

    private void retrieveSuspenseByPK(IProxy proxy, String suspensePK) throws Throwable
    {
        String suspenseVOAsXML = (String) proxy.invoke("retrieveSuspenseByPK", new Object[] {suspensePK});

        SuspenseVO suspenseVO = (SuspenseVO) Util.unmarshalVO(SuspenseVO.class, suspenseVOAsXML);

        if (debug)
        {
            System.out.println("SOAPClient.retrieveSuspenseByPK");

            if (suspenseVO != null)
            {
                System.out.println("suspenseVO.getSuspensePK() = " + suspenseVO.getSuspensePK());
                System.out.println("suspenseVO.getSuspenseType() = " + suspenseVO.getSuspenseType());
                System.out.println("suspenseVO].getEffectiveDate() = " + suspenseVO.getEffectiveDate());
            }
            else
            {
                System.out.println("No Results");
            }
        }
    }

    private void retrieveSuspenseByUserDefNumber(IProxy proxy, String userDefNumber) throws Throwable
    {
        String[] suspenseVOAsXML = (String[]) proxy.invoke("retrieveSuspenseByUserDefNumber", new Object[] {userDefNumber});

        SuspenseVO[] suspenseVOs = (SuspenseVO[]) Util.unmarshalVOs(SuspenseVO.class, suspenseVOAsXML);

        if (debug)
        {
            System.out.println("SOAPClient.retrieveSuspenseByUserDefNumber");

            if (suspenseVOs != null)
            {
                System.out.println("suspenseVOs.length = " + suspenseVOs.length);

                for (int i = 0; i < suspenseVOs.length; i++)
                {
                    System.out.println("i = " + i + ", suspenseVOs[i].getSuspensePK() = " + suspenseVOs[i].getSuspensePK());
                    System.out.println("i = " + i + ", suspenseVOs[i].getSuspenseType() = " + suspenseVOs[i].getSuspenseType());
                    System.out.println("i = " + i + ", suspenseVOs[i].getEffectiveDate() = " + suspenseVOs[i].getEffectiveDate());
                }
            }
            else
            {
                System.out.println("No Results");
            }
        }

    }

    private void getCodeTableDescription(IProxy proxy, String codeTableName, String code) throws Throwable
    {
//        String codeTableName = "STATE";
//        String code = "CT";

        String description = (String) proxy.invoke("getCodeTableDescription", new Object[] {codeTableName, code});

        if (debug)
        {
            System.out.println("SOAPClient.getCodeTableDescription");

            System.out.println("description = " + description);
        }
    }

    private void getCodeTableEntries(IProxy proxy, String codeTableName) throws Throwable
    {
//        String codeTableName = "STATE";

        String[] codeTableVOAsXML = (String[]) proxy.invoke("getCodeTableEntries", new Object[] {codeTableName});

        CodeTableVO[] codeTableVOs = (CodeTableVO[]) Util.unmarshalVOs(CodeTableVO.class, codeTableVOAsXML);

        if (debug)
        {
            System.out.println("SOAPClient.getCodeTableEntries");

            if (codeTableVOs != null)
            {
                System.out.println("codeTableVOs.length = " + codeTableVOs.length);

                for (int i = 0; i < codeTableVOs.length; i++)
                {
                    System.out.println("i = " + i + ", codeTableVOs[i].getCode() = " + codeTableVOs[i].getCode());
                    System.out.println("i = " + i + ", codeTableVOs[i].getCodeDesc() = " + codeTableVOs[i].getCodeDesc());
                }
            }
            else
            {
                System.out.println("No Results");
            }
        }
    }
    
    
    //************************** INTERNAL METHODS ************************************
    
    private ProductStructureVO buildProductStructureVO(String companyName, String marketingPackageName,
                                                       String groupProductName, String areaName, String businessContractName)
    {
        ProductStructureVO companyStructureVO = new ProductStructureVO();
        
//        companyStructureVO.setCompanyName(companyName);
        companyStructureVO.setMarketingPackageName(marketingPackageName);
        companyStructureVO.setGroupProductName(groupProductName);
        companyStructureVO.setAreaName(areaName);
        companyStructureVO.setBusinessContractName(businessContractName);
        companyStructureVO.setTypeCodeCT("Product");

        return companyStructureVO;
    }
    
    private ContractClientVO buildContractClientVO(String clientRoleFK, String effectiveDate, String terminationDate, String roleType)
    {
        ContractClientVO contractClientVO = new ContractClientVO();
        
        contractClientVO.setClientRoleFK(new Long(clientRoleFK).longValue()); 
        contractClientVO.setEffectiveDate(effectiveDate);
        contractClientVO.setTerminationDate(terminationDate);
        if (roleType.equalsIgnoreCase("Insured"))
        {
            contractClientVO.setClassCT("NonSmoker");
            contractClientVO.setUnderwritingClassCT("Preferred");
        }

        return contractClientVO;
    }
    
    private InvestmentVO buildInvestmentVO(String allocationPercent, String filteredFundFK)
    {
        InvestmentVO investmentVO = new InvestmentVO();
        
        investmentVO.setFilteredFundFK(new Long(filteredFundFK).longValue());

        InvestmentAllocationVO investmentAllocationVO = new InvestmentAllocationVO();
        investmentAllocationVO.setAllocationPercent(new EDITBigDecimal(allocationPercent, 10).getBigDecimal());
        investmentAllocationVO.setOverrideStatus("P");

        investmentVO.addInvestmentAllocationVO(investmentAllocationVO);

        return investmentVO;
    }
    
    private EDITTrxVO buildEDITTrxVO(String effectiveDate, String pendingStatus, String sequenceNumber, String taxYear,
                                     String trxAmount, String transactionTypeCT, String trxIsRescheduledInd, String noAccountingInd,
                                     String noCommissionInd, String noCorrespondenceInd)
    {
        EDITTrxVO editTrxVO = new EDITTrxVO();
        
        editTrxVO.setEffectiveDate(effectiveDate);  // don't make older than date it was issued because it will undo the issue trx
        editTrxVO.setPendingStatus(pendingStatus);
        editTrxVO.setSequenceNumber(new Integer(sequenceNumber).intValue());
        editTrxVO.setTaxYear(new Integer(taxYear).intValue());
        editTrxVO.setTrxAmount(new EDITBigDecimal(trxAmount).getBigDecimal());
//        editTrxVO.setDueDate();   // only if have scheduledEvent
        editTrxVO.setTransactionTypeCT(transactionTypeCT);
        editTrxVO.setTrxIsRescheduledInd(trxIsRescheduledInd);
        editTrxVO.setNoAccountingInd(noAccountingInd);
        editTrxVO.setNoCommissionInd(noCommissionInd);
        editTrxVO.setNoCorrespondenceInd(noCorrespondenceInd);
        
        return editTrxVO;
    }

    private void populateVarLife(IProxy proxy) throws Throwable
    {
        String companyName = "SEG";
         String marketingPackageName = "Series7";
         String groupProductName = "*";
         String areaName = "*";
         String businessContractName = "VarLife";

         ProductStructureVO companyStructureVO = buildProductStructureVO(
                     companyName, marketingPackageName, groupProductName, areaName, businessContractName);

        String effectiveDate1 = "2005/04/02";
//        int numberOfContracts1 = 50000;
        int numberOfContracts1 = 1;
        String contractNumberRoot1 = "VAR04";
        String clientFirstName1 = "Tim";
        String clientLastNameRoot1 = "Smith";
        String genderCT1 = "Male";
        String birthDate1 = "1960/01/01";
        String clientId1 = "111000001";

        String effectiveDate2 = "2005/08/01";
//        int numberOfContracts2 = 50000;
        int numberOfContracts2 = 1;
        String contractNumberRoot2 = "VAR08";
        String clientFirstName2 = "Mary";
        String clientLastNameRoot2 = "Jones";
        String genderCT2 = "Female";
        String birthDate2 = "1964/02/01";
        String clientId2 = "222000001";


        ArrayList roleTypeCTs = new ArrayList();  // Owner and insured
        roleTypeCTs.add("OWN");
        roleTypeCTs.add("Insured");


        //  1st set
        createContracts(proxy, contractNumberRoot1, numberOfContracts1, effectiveDate1, clientFirstName1, clientLastNameRoot1,
                    clientId1, birthDate1, genderCT1, roleTypeCTs, companyStructureVO);

        // 2nd set
        createContracts(proxy, contractNumberRoot2, numberOfContracts2, effectiveDate2, clientFirstName2, clientLastNameRoot2,
                    clientId2, birthDate2, genderCT2, roleTypeCTs, companyStructureVO);
    }

    private void populateEIA(IProxy proxy)  throws Throwable
    {
        String companyName = "SEG";
        String marketingPackageName = "*";
        String groupProductName = "*";
        String areaName = "*";
        String businessContractName = "EIA";

        ProductStructureVO companyStructureVO = buildProductStructureVO(
                    companyName, marketingPackageName, groupProductName, areaName, businessContractName);

        String effectiveDate1 = "2004/08/01";
//        int numberOfContracts1 = 50000;
        int numberOfContracts1 = 2;
        String contractNumberRoot1 = "EIA08";
        String clientFirstName1 = "Jack";
        String clientLastNameRoot1 = "Peters";
        String genderCT1 = "Male";
        String birthDate1 = "1950/03/01";
        String clientId1 = "333000001";
        
        String effectiveDate2 = "2004/02/01";
//        int numberOfContracts2 = 88000;
        int numberOfContracts2 = 2;
        String contractNumberRoot2 = "EIA02";
        String clientFirstName2 = "Jane";
        String clientLastNameRoot2 = "Franklin";
        String genderCT2 = "Female";
        String birthDate2 = "1955/04/15";
        String clientId2 = "444000001";

        String effectiveDate3 = "2004/02/01";
//        int numberOfContracts3 = 5000;
        int numberOfContracts3 = 2;
        String contractNumberRoot3 = "SUR";    // Surrender
        String clientFirstName3 = "Greg";
        String clientLastNameRoot3 = "Jenkins";
        String genderCT3 = "Male";
        String birthDate3 = "1970/05/15";
        String clientId3 = "555000001";

        String effectiveDate4 = "2004/02/01";
//        int numberOfContracts4 = 3000;
        int numberOfContracts4 = 2;
        String contractNumberRoot4 = "TRAN";    // Transfers
        String clientFirstName4 = "Kate";
        String clientLastNameRoot4 = "Adams";
        String genderCT4 = "Female";
        String birthDate4 = "1972/06/01";
        String clientId4 = "666000001";

        String effectiveDate5 = "2004/02/01";
//        int numberOfContracts5 = 2000;
        int numberOfContracts5 = 2;
        String contractNumberRoot5 = "WD";    // WD
        String clientFirstName5 = "Joe";
        String clientLastNameRoot5 = "Burk";
        String genderCT5 = "Male";
        String birthDate5 = "1975/07/01";
        String clientId5 = "777000001";

        String effectiveDate6 = "2004/02/01";
//        int numberOfContracts6 = 2000;
        int numberOfContracts6 = 2;
        String contractNumberRoot6 = "DTH";    // Death
        String clientFirstName6 = "Lisa";
        String clientLastNameRoot6 = "Chapman";
        String genderCT6 = "Female";
        String birthDate6 = "1976/08/01";
        String clientId6 = "888000001";


        ArrayList roleTypeCTs = new ArrayList();  // Owner and insured
        roleTypeCTs.add("OWN");
        roleTypeCTs.add("ANN");

        //  1st set
        createContracts(proxy, contractNumberRoot1, numberOfContracts1, effectiveDate1, clientFirstName1, clientLastNameRoot1,
                    clientId1, birthDate1, genderCT1, roleTypeCTs, companyStructureVO);

        // 2nd set
        createContracts(proxy, contractNumberRoot2, numberOfContracts2, effectiveDate2, clientFirstName2, clientLastNameRoot2,
                    clientId2, birthDate2, genderCT2, roleTypeCTs, companyStructureVO);

        //  3rd set
        createContracts(proxy, contractNumberRoot3, numberOfContracts3, effectiveDate3, clientFirstName3, clientLastNameRoot3,
                    clientId3, birthDate3, genderCT3, roleTypeCTs, companyStructureVO);

        // 4th set
        createContracts(proxy, contractNumberRoot4, numberOfContracts4, effectiveDate4, clientFirstName4, clientLastNameRoot4,
                    clientId4, birthDate4, genderCT4, roleTypeCTs, companyStructureVO);

        // 5th set
        createContracts(proxy, contractNumberRoot5, numberOfContracts5, effectiveDate5, clientFirstName5, clientLastNameRoot5,
                    clientId5, birthDate5, genderCT5, roleTypeCTs, companyStructureVO);

         // 6th set
        createContracts(proxy, contractNumberRoot6, numberOfContracts6, effectiveDate6, clientFirstName6, clientLastNameRoot6,
                    clientId6, birthDate6, genderCT6, roleTypeCTs, companyStructureVO);
    }
    
    private void createContracts(IProxy proxy, String contractNumberRoot, int numberOfContracts, String effectiveDate,
                                String clientFirstName, String clientLastNameRoot, String clientId, String birthDate,
                                String genderCT, ArrayList roleTypeCTs, ProductStructureVO companyStructureVO) throws Throwable
    {
        for (int i = 0; i < numberOfContracts; i++)
        {
            String contractNumber = contractNumberRoot + (i+1);

            ArrayList clientRolePKs = new ArrayList();

            addClient(proxy, clientId, birthDate, genderCT, clientFirstName, (clientLastNameRoot + (i+1)));

            for (int j = 0; j < roleTypeCTs.size(); j++)
            {
                String clientRolePK = addClientRole(proxy, clientId, (String) roleTypeCTs.get(j));
                clientRolePKs.add(clientRolePK);
            }

            SegmentVO segmentVO = null;
            if (contractNumberRoot.startsWith("VAR"))
            {
                segmentVO = addPolicyLife(proxy, contractNumber, effectiveDate, companyStructureVO, clientRolePKs);
                processPremiumLife(proxy, segmentVO, numberOfContracts);
            }
            else
            {
                segmentVO = addPolicyEIA(proxy, contractNumber, effectiveDate, companyStructureVO, clientRolePKs);
                processEIATransactions(proxy, segmentVO, numberOfContracts);
            }

            clientId = adjustClientId(clientId, i+1);

            System.out.println("Contract created : " + contractNumber);
        }
    }


    private String adjustClientId(String clientId, int counter)
    {
        String newClientId = clientId.substring(0, 3);
        counter++;
        String count = counter + "";

        int clientIdLth = newClientId.length() + count.length();

        int padFactor = 9 - clientIdLth;
        String zeros = null;

        if (padFactor == 5)
        {
            zeros = "00000";
        }
        else if (padFactor == 4)
        {
            zeros = "0000";
        }
        else if (padFactor == 3)
        {
            zeros = "000";
        }
        else if (padFactor == 2)
        {
            zeros = "00";
        }
         else if (padFactor == 1)
        {
            zeros = "0";
        }

        return newClientId + zeros + count;
    }

    private SegmentVO addPolicyEIA(IProxy proxy, String contractNumber, String effectiveDate, ProductStructureVO companyStructureVO, ArrayList clientRolePKs)  throws Throwable
    {
//        String masterNumber = contractNumber;
        String caseNumber = " ";

        SegmentVO segmentVO = new SegmentVO();
        segmentVO.setContractNumber(contractNumber);
        segmentVO.setEffectiveDate(effectiveDate);
        segmentVO.setIssueDate(effectiveDate);
        segmentVO.setAmount(new EDITBigDecimal("0").getBigDecimal());
        segmentVO.setSegmentNameCT("DFA");
        segmentVO.setOptionCodeCT("DFA");
        segmentVO.setSegmentStatusCT("Pending");
        segmentVO.setIssueStateCT("CT");
        segmentVO.setQualNonQualCT("NonQualified");
        segmentVO.setLastAnniversaryDate(effectiveDate);
        segmentVO.setApplicationSignedDate(effectiveDate);
        segmentVO.setApplicationReceivedDate(effectiveDate);
        segmentVO.setWaiverInEffect("N");
        segmentVO.setCreationDate(effectiveDate);
        segmentVO.setCreationOperator("System");

        PayoutVO payoutVO = new PayoutVO();
        payoutVO.setCertainPeriodEndDate("9999/12/31");
        payoutVO.setFinalPaymentDate("9999/12/31");
        payoutVO.setPaymentStartDate("9999/12/31");
        payoutVO.setNextPaymentDate("9999/12/31");

        segmentVO.addPayoutVO(payoutVO);

        //  Add Investments
        InvestmentVO investmentVO1 = buildInvestmentVO(".6", "1073582841765");
        InvestmentVO investmentVO2 = buildInvestmentVO(".4", "1073582841225");

        segmentVO.addInvestmentVO(investmentVO1);
        segmentVO.addInvestmentVO(investmentVO2);

        //  Add ContractClients

        // OWNER
        ContractClientVO contractClientVOOwner = buildContractClientVO((String) clientRolePKs.get(0), effectiveDate, EDITDate.DEFAULT_MAX_DATE, "OWN");

        // ANNUITANT
        ContractClientVO contractClientVOAnnuitant = buildContractClientVO((String) clientRolePKs.get(1), effectiveDate, EDITDate.DEFAULT_MAX_DATE, "Insured");


        InduaseContractClientVO induaseContractClientVOOwner = new InduaseContractClientVO();
        induaseContractClientVOOwner.setContractClientVO(contractClientVOOwner);

        InduaseContractClientVO induaseContractClientVOAnnuitant = new InduaseContractClientVO();
        induaseContractClientVOAnnuitant.setContractClientVO(contractClientVOAnnuitant);

        InduaseContractClientVO[] induaseContractClientVOs = {induaseContractClientVOOwner, induaseContractClientVOAnnuitant};



        String segmentAsXML = Util.marshalVO(segmentVO);
        String companyStructureAsXML = Util.marshalVO(companyStructureVO);
        String[] induaseContractClientsAsXML = Util.marshalVOs(induaseContractClientVOs);

        String segmentPK = (String) proxy.invoke("addPolicy", new Object[] {segmentAsXML, caseNumber,
                                 companyStructureAsXML,
                                 induaseContractClientsAsXML});


        if (debug)
        {
            System.out.println("SOAPClient.addPolicy");

            if (segmentPK != null)
            {
                System.out.println("segmentPK = " + segmentPK);
            }
            else
            {
                System.out.println("No Results");
            }
        }

        return segmentVO;
    }

    public ProductStructureVO populateProductStructure(IProxy proxy)  throws Throwable
    {
        String companyName = "SEG";
        String marketingPackageName = "Series7";
        String groupProductName = "*";
        String areaName = "*";
        String businessContractName = "VarLife";

        ProductStructureVO companyStructureVO = buildProductStructureVO(
                    companyName, marketingPackageName, groupProductName, areaName, businessContractName);

        String companyStructureAsXML = Util.marshalVO(companyStructureVO);

        String companyStructurePK = (String) proxy.invoke("addProductStructure", new Object[] {companyStructureAsXML});

        if (debug)
        {
            System.out.println("SOAPClient.addProductStructure");

            if (companyStructurePK != null)
            {
                System.out.println("companyStructurePK = " + companyStructurePK);
            }
            else
            {
                System.out.println("No Results");
            }
        }

        return companyStructureVO;
    }
}