/*
 * User: dlataill
 * Date: May 20, 2004
 * Time: 7:30:58 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package extension.component;

import extension.business.InduaseAdapter;
import edit.services.component.AbstractComponent;
import edit.common.vo.*;
import edit.common.EDITDate;
import edit.common.CodeTableWrapper;
import edit.common.EDITDateTime;

import fission.utility.Util;

import java.util.ArrayList;
import java.util.List;

import contract.component.ContractComponent;
import contract.business.Contract;
import engine.*;


/**
 * A specialized interface for the Induase company to access the EDITSolutions system via their own user interface.
 * <P>
 * This class does not expect the caller to supply primary and foreign key values.  If the keys are provided, they are
 * ignored.
 * <P>
 * For "add" and "save" methods, the primary key is set to zero.  For "update" methods, the primary key is set to the
 * key found in the database.
 */
public class InduaseAdapterComponent extends AbstractComponent implements InduaseAdapter
{
    /**
     * @see  extension.business.InduaseAdapter#addClient
     */
    public String addClient(String clientDetailAsXML) throws Throwable
    {
        ClientDetailVO clientDetailVO = (ClientDetailVO) Util.unmarshalVO(ClientDetailVO.class, clientDetailAsXML);

        //  Overlay values
        this.setClientDetailVODefaults(clientDetailVO);

        //  Save
        client.business.Client clientComponent = new client.component.ClientComponent();

        long pk = clientComponent.saveOrUpdateClient(clientDetailVO, true);

        return Long.toString(pk);
    }

    /**
     * @see  extension.business.InduaseAdapter#addClientRole
     */
    public String addClientRole(String clientIdentification, String clientRoleAsXML) throws Throwable
    {
        //  Convert XML to appropriate VOs
        ClientRoleVO clientRoleVO = (ClientRoleVO) Util.unmarshalVO(ClientRoleVO.class, clientRoleAsXML);

        //  Find ClientDetail by client id
        client.business.Lookup clientLookupComponent = new client.component.LookupComponent();
        ClientDetailVO[] clientDetailVOs = clientLookupComponent.getClientDetailByClientId(clientIdentification, false, null);

        //  Default the PKs to zero for a save
        clientRoleVO.setClientRolePK(0);

        //  Overlay clientRoleVO
        clientRoleVO.setClientDetailFK(clientDetailVOs[0].getClientDetailPK());
        clientRoleVO.setOverrideStatus("P");

        //  Save ClientRoleVO, get its pk
        role.business.Role roleComponent = new role.component.RoleComponent();
        long clientRolePK = roleComponent.saveOrUpdateClientRole(clientRoleVO);

        return Long.toString(clientRolePK);
    }

    /**
     * @see extension.business.InduaseAdapter#addContractClientToExistingContractAndClientRole
     */
    public String addContractClientToExistingContractAndClientRole(String contractNumber, String induaseContractClientAsXML)
            throws Throwable
    {
         //  Convert XML to appropriate VOs
        InduaseContractClientVO induaseContractClientVO = (InduaseContractClientVO) Util.unmarshalVO(InduaseContractClientVO.class, induaseContractClientAsXML);

        //  Find Segment by contractNumber
        SegmentVO segmentVO = this.composeBaseSegmentVOByContractNumber(contractNumber);

        //  Convert the InduaseContractClientVO to a properly populated ContractClient
        ContractClientVO contractClientVO = convertToContractClient(induaseContractClientVO);

        //  Overlay segmentFK
        contractClientVO.setSegmentFK(segmentVO.getSegmentPK());

        //  Get the BillLapse and the Billing
//        BillLapseVO billLapseVO = contractClientVO.getBillLapseVO()[0];
//        BillingVO billingVO = (BillingVO) billLapseVO.getParentVO(BillingVO.class);

        //  Save ContractClientVO
        contract.business.Contract contractComponent = new contract.component.ContractComponent();
        long contractClientPK = contractComponent.saveContractClientVO(contractClientVO);

        //  Return contractClientPK
        return Long.toString(contractClientPK);
    }

    /**
     * @see  extension.business.InduaseAdapter#addSuspense
     */
    public String addSuspense(String suspenseAsXML) throws Throwable
    {
        SuspenseVO suspenseVO = (SuspenseVO) Util.unmarshalVO(SuspenseVO.class, suspenseAsXML);

        //  Overlay values
        this.setSuspenseVODefaults(suspenseVO);
        suspenseVO.setSuspensePK(0);    // this is new, override their pk in case it is erroneously set

        //  Save
        event.business.Event eventComponent = new event.component.EventComponent();

        long pk = eventComponent.saveSuspense(suspenseVO);

        return Long.toString(pk);
    }

    /**
     * @see  extension.business.InduaseAdapter#addPolicy
     */
    public String addPolicy(String segmentAsXML, String caseNumber, String productStructureAsXML,
                            String[] induaseContractClientsAsXML) throws Throwable
    {
        //  Convert XML to VOs
        SegmentVO segmentVO = (SegmentVO) Util.unmarshalVO(SegmentVO.class, segmentAsXML);
        ProductStructureVO productStructureVO = (ProductStructureVO) Util.unmarshalVO(ProductStructureVO.class, productStructureAsXML);
        InduaseContractClientVO[] induaseContractClientVOs = (InduaseContractClientVO[]) Util.unmarshalVOs(InduaseContractClientVO.class, induaseContractClientsAsXML);

        //  Default PK to zero for save
        segmentVO.setSegmentPK(0);

        //  Find the PolicyGroup and set the Segment's FK
//        if ((caseNumber != null) || (! caseNumber.equals("")))
//        {
//            // They provided a caseNumber, go find the appropriate PolicyGroup
//            policyGroup = PolicyGroup.findByCaseNumber(caseNumber);
//
//            //  Associate the policyGroup to the segment
//            segmentVO.setPolicyGroupFK(policyGroup.getPolicyGroupPK().longValue());
//        }

        //  Find the product structure and set the Segment's FK
        engine.business.Lookup engineLookupComponent = new engine.component.LookupComponent();

        Company company = Company.findByPK(productStructureVO.getCompanyFK());
        ProductStructureVO[] existingProductStructureVOs = engineLookupComponent.findProductStructureByNames(
                company.getCompanyName(), productStructureVO.getMarketingPackageName(),
                productStructureVO.getGroupProductName(), productStructureVO.getAreaName(),
                productStructureVO.getBusinessContractName());

        if (existingProductStructureVOs != null)
        {
            segmentVO.setProductStructureFK(existingProductStructureVOs[0].getProductStructurePK());
        }

        //  Convert the InduaseContractClient contents to ContractClients with billing info and attach to segment
        if (induaseContractClientVOs != null)
        {
            for (int i = 0; i < induaseContractClientVOs.length; i++)
            {
                ContractClientVO contractClientVO = convertToContractClient(induaseContractClientVOs[i]);

                segmentVO.addContractClientVO(contractClientVO);
            }
        }

        //  Save
        contract.business.Contract contractComponent = new contract.component.ContractComponent();
        long segmentPK = contractComponent.saveSegment(segmentVO, "", false, null);

        return Long.toString(segmentPK);
    }


    /**
     * @see  extension.business.InduaseAdapter#deleteClient
     */
    public void deleteClient(String clientIdentification) throws Throwable
    {
        client.business.Lookup lookupComponent = new client.component.LookupComponent();

        ClientDetailVO[] clientDetailVOs = lookupComponent.getClientDetailByClientId(clientIdentification, false, null);

        client.business.Client clientComponent = new client.component.ClientComponent();

        clientComponent.deleteClient(clientDetailVOs[0].getClientDetailPK());
    }

    /**
     * @see  extension.business.InduaseAdapter#deleteSuspense
     */
    public void deleteSuspense(String suspensePK) throws Throwable
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        eventComponent.deleteSuspense(Long.parseLong(suspensePK));
    }

    /**
     * @see  extension.business.InduaseAdapter#updateClient
     */
    public void updateClient(String clientDetailAsXML) throws Throwable
    {
        ClientDetailVO clientDetailVO = (ClientDetailVO) Util.unmarshalVO(ClientDetailVO.class, clientDetailAsXML);

        //  Find existing ClientDetail to get pk
        client.business.Lookup lookupComponent = new client.component.LookupComponent();

        ClientDetailVO[] clientDetailVOs = lookupComponent.getClientDetailByClientId(clientDetailVO.getClientIdentification(),
                false, null);

        //  Overlay values
        this.setClientDetailVODefaults(clientDetailVO);     // sets pk to zero
        clientDetailVO.setClientDetailPK(clientDetailVOs[0].getClientDetailPK());   // need pk for update, otherwise saves as new

        //  Save
        client.business.Client clientComponent = new client.component.ClientComponent();

        clientComponent.saveOrUpdateClient(clientDetailVO, true);
    }

    /**
     * @see  extension.business.InduaseAdapter#updateSuspense
     */
    public void updateSuspense(String suspenseAsXML) throws Throwable
    {
        SuspenseVO suspenseVO = (SuspenseVO) Util.unmarshalVO(SuspenseVO.class, suspenseAsXML);

        //  Overlay values
        this.setSuspenseVODefaults(suspenseVO);

        //  Save
        event.business.Event eventComponent = new event.component.EventComponent();

        eventComponent.saveSuspense(suspenseVO);
    }

    /**
     * @see  extension.business.InduaseAdapter#updatePolicy
     */
    public void updatePolicy(String segmentAsXML) throws Throwable
    {
        //  Convert XML to VOs
        SegmentVO segmentVO = (SegmentVO) Util.unmarshalVO(SegmentVO.class, segmentAsXML);

        //  Get existing segmentVO
        SegmentVO existingSegmentVO  = this.composeBaseSegmentVOByContractNumber(segmentVO.getContractNumber());

        //  Overlay keys into new segment if not already set
        if (segmentVO.getSegmentPK() == 0) segmentVO.setSegmentPK(existingSegmentVO.getSegmentPK());
        if (segmentVO.getContractGroupFK() == 0) segmentVO.setContractGroupFK(existingSegmentVO.getContractGroupFK());
        if (segmentVO.getProductStructureFK() == 0) segmentVO.setProductStructureFK(existingSegmentVO.getProductStructureFK());

        //  Save
        contract.business.Contract contractComponent = new contract.component.ContractComponent();
        contractComponent.saveSegment(segmentVO, "", true, "SYSTEM");
    }

    /**
     * @see  extension.business.InduaseAdapter#saveGroupMaster
     */
//    public void saveGroupMaster(String masterAsXML, String billingAsXML) throws Throwable
//    {
//        // Convert XML to VO
//        MasterVO masterVO = (MasterVO) Util.unmarshalVO(MasterVO.class, masterAsXML);
//        BillingVO billingVO = (BillingVO) Util.unmarshalVO(BillingVO.class, billingAsXML);
//
//        //  Set pk to zero for saving
//        masterVO.setMasterPK(0);
//
//        //  Save
//        contract.business.Contract contractComponent = new contract.component.ContractComponent();
//
//        contractComponent.saveGroupMaster(masterVO, billingVO);
//    }

    /**
     * @see  extension.business.InduaseAdapter#saveListMaster
     */
//    public void saveListMaster(String masterAsXML) throws Throwable
//    {
//        // Convert XML to VO
//        MasterVO masterVO = (MasterVO) Util.unmarshalVO(MasterVO.class, masterAsXML);
//
//        //  Set pk to zero for saving
//        masterVO.setMasterPK(0);
//
//        //  Save
//        contract.business.Contract contractComponent = new contract.component.ContractComponent();
//
//        contractComponent.saveListMaster(masterVO);
//    }

    /**
     * @see extension.business.InduaseAdapter#issueNewBusiness
     */
    public void issueNewBusiness(String contractNumber, String issueDate, String operator, String lastDayOfMonthInd) throws Throwable
    {
        //  Find existing Segment via contract number
        SegmentVO baseSegmentVO = this.composeBaseSegmentVOByContractNumber(contractNumber);

        setSegmentDefaults(baseSegmentVO);

        //  If they supplied an issueDate override the defaults
        if (issueDate != null)
        {
            baseSegmentVO.setIssueDate(issueDate);
        }

        //  If they didn't provide an operator, default it
        if (operator == null)
        {
            operator = "System";
        }

        //  If they didn't provide a last day of month indicator, default it
        if (lastDayOfMonthInd == null)
        {
            lastDayOfMonthInd = "N";
        }

        event.business.Event eventComponent = new event.component.EventComponent();

        eventComponent.commitContract(baseSegmentVO, operator, lastDayOfMonthInd, null, "N");
    }

    /**
     * @see  extension.business.InduaseAdapter#processInforceQuote
     */
    public String processInforceQuote(String contractNumber, String quoteDate) throws Throwable
    {
        QuoteVO quoteVO = null;

        //  Get the appropriate segment based on the contract number
        SegmentVO segmentVO = this.composeBaseSegmentVOByContractNumber(contractNumber);

        String effectiveDate = segmentVO.getEffectiveDate();

        if (quoteDate.compareTo(effectiveDate) >= 0)
        {
            event.business.Event eventComponent = new event.component.EventComponent();
            quoteVO = eventComponent.performInforceQuote(quoteDate, "CashTerminationValue", segmentVO.getSegmentPK(), "NA");
        }

        return Util.marshalVO(quoteVO);
    }

    /**
     * @see  extension.business.InduaseAdapter#processTransaction
     */
    public void processTransaction(String contractNumber, String groupSetupAsXML, String editTrxAsXML) throws Throwable
    {
        //  Convert the strings to the proper objects
        GroupSetupVO groupSetupVO = (GroupSetupVO) Util.unmarshalVO(GroupSetupVO.class, groupSetupAsXML);
        EDITTrxVO editTrxVO = (EDITTrxVO) Util.unmarshalVO(EDITTrxVO.class, editTrxAsXML);

        //  Set the PKs for saving
        groupSetupVO.setGroupSetupPK(0);
        editTrxVO.setEDITTrxPK(0);

        //  Get the appropriate segment based on the contract number
        SegmentVO segmentVO = this.composeBaseSegmentVOByContractNumber(contractNumber);

        //  Get the transactionType
        String transactionType = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("TRXTYPE", editTrxVO.getTransactionTypeCT());

        //  Overlay the EDITTrxVO with any default values
        this.overlayEDITTrxVO(editTrxVO);

        //  Overlay the GroupSetupVO with any default values
        this.overlayGroupSetupVO(groupSetupVO);

        //  Overlay ContractSetup's segmentFK with the found base Segment specified by the contractNumber
        ContractSetupVO[] contractSetupVOs = groupSetupVO.getContractSetupVO();
        for (int i = 0; i < contractSetupVOs.length; i++)
        {
            contractSetupVOs[i].setSegmentFK(segmentVO.getSegmentPK());
        }

        //  Attach EDITTrxCorrespondence if necessary
//        this.attachCorrespondence(editTrxVO);

        //  Save and process the transaction
        event.business.Event eventComponent = new event.component.EventComponent();
        eventComponent.saveGroupSetup(groupSetupVO, editTrxVO, transactionType, segmentVO.getOptionCodeCT(), segmentVO.getProductStructureFK());
    }

    /**
     * @see  extension.business.InduaseAdapter#deleteTransaction
     */
    public void deleteTransaction(String editTrxPK) throws Throwable
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        eventComponent.deleteClientTrx(Long.parseLong(editTrxPK), "NA");
    }

    /**
     * @see  extension.business.InduaseAdapter#updateTransaction
     */
    public void updateTransaction(String contractNumber, String groupSetupAsXML, String editTrxAsXML) throws Throwable
    {
        //  Convert the strings to the proper objects
        GroupSetupVO groupSetupVO = (GroupSetupVO) Util.unmarshalVO(GroupSetupVO.class, groupSetupAsXML);
        EDITTrxVO editTrxVO = (EDITTrxVO) Util.unmarshalVO(EDITTrxVO.class, editTrxAsXML);

//        //  Set the PKs for saving
//        groupSetupVO.setGroupSetupPK(0);
//        editTrxVO.setEDITTrxPK(0);

        //  Get the appropriate segment based on the contract number
        SegmentVO segmentVO = this.composeBaseSegmentVOByContractNumber(contractNumber);

        //  Get the transactionType
        String transactionType = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("TRXTYPE", editTrxVO.getTransactionTypeCT());

        //  Overlay the EDITTrxVO with any default values
        this.overlayEDITTrxVO(editTrxVO);

        //  Overlay the GroupSetupVO with any default values
        this.overlayGroupSetupVO(groupSetupVO);

        //  Overlay ContractSetup's segmentFK with the found base Segment specified by the contractNumber
        ContractSetupVO[] contractSetupVOs = groupSetupVO.getContractSetupVO();
        for (int i = 0; i < contractSetupVOs.length; i++)
        {
            contractSetupVOs[i].setSegmentFK(segmentVO.getSegmentPK());
        }

        //  Attach EDITTrxCorrespondence if necessary
//        this.attachCorrespondence(editTrxVO);

        //  Save and process the transaction
        event.business.Event eventComponent = new event.component.EventComponent();
        eventComponent.saveGroupSetup(groupSetupVO, editTrxVO, transactionType, segmentVO.getOptionCodeCT(), segmentVO.getProductStructureFK());
    }

    /**
     * @see extension.business.InduaseAdapter#reverseHistory
     */
    public void reverseHistory(String contractNumber, String historyPK, String historyType) throws Exception
    {
        contract.business.Lookup contractLookup = new contract.component.LookupComponent();
        contract.business.Contract contractComponent = new contract.component.ContractComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(SegmentVO.class);

        SegmentVO[] segmentVO = contractLookup.getSegmentByContractNumber(contractNumber, true, new ArrayList());

        SegmentVO[] riderSegments = segmentVO[0].getSegmentVO();

        if (historyType.equalsIgnoreCase("ChangeHistory"))
        {
            ChangeHistoryVO[] changeHistory = contractLookup.getChangeHistoryByPK(Long.parseLong(historyPK));

            String changeHistoryEffectiveDate = changeHistory[0].getEffectiveDate();
            String contractEffectiveDate  = segmentVO[0].getEffectiveDate();

            EDITDate currentDate = new EDITDate();

            if (changeHistoryEffectiveDate.equals(contractEffectiveDate))
            {
                segmentVO[0].setSegmentStatusCT("Pending");
                segmentVO[0].setStatusChangeDate(currentDate.getFormattedDate());
//                segmentVO[0].removeAllChangeHistoryVO();

                if (riderSegments != null)
                {
                    for (int i = 0; i < riderSegments.length; i++)
                    {
                        riderSegments[i].setSegmentStatusCT("Pending");
                        riderSegments[i].setStatusChangeDate(currentDate.getFormattedDate());
//                        riderSegments[i].removeAllChangeHistoryVO();
                    }
                }

                contractComponent.saveSegment(segmentVO[0], "", false, null);

                List segmentPKList = new ArrayList();

                segmentPKList.add(new Long(segmentVO[0].getSegmentPK()));

                SegmentVO[] riderSegmentVOs = contractLookup.findRiderSegmentsBy_SegmentPK(segmentVO[0].getSegmentPK());
                if (riderSegmentVOs != null && riderSegmentVOs.length > 0)
                {
                    for (int i = 0; i < riderSegmentVOs.length; i++)
                    {
                        segmentPKList.add(new Long(riderSegmentVOs[i].getSegmentPK()));
                    }
                }

                voInclusionList.clear();
                voInclusionList.add(EDITTrxHistoryVO.class);
                voInclusionList.add(EDITTrxCorrespondenceVO.class);

                event.business.Event eventComponent = new event.component.EventComponent();
                eventComponent.updateIssueEDITTrxVO(segmentVO[0].getSegmentPK(), voInclusionList);
                eventComponent.deletePendingTrxBySegmentPK(segmentPKList, voInclusionList);
            }
        }
        else
        {
            String editTrxPK = historyPK;

            event.business.Event eventComponent = new event.component.EventComponent();

            eventComponent.reverseClientTrx(Long.parseLong(editTrxPK), "NA", null);
        }
	}

    /**
     * @see  extension.business.InduaseAdapter#retrieveAllPendingTransactions
     */
    public String[] retrieveAllPendingTransactions() throws Throwable
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        List voInclusionList = new ArrayList();

        EDITTrxVO[] editTrxVOs = eventComponent.composeEDITTrxVOByPendingStatus("P", voInclusionList);

        return Util.marshalVOs(editTrxVOs);
    }

    /**
     * @see  extension.business.InduaseAdapter#retrieveAllPendingTransactionsForContract
     */
    public String[] retrieveAllPendingTransactionsForContract(String contractNumber) throws Throwable
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        List voInclusionList = new ArrayList();

        contract.business.Lookup lookupComponent = new contract.component.LookupComponent();

        SegmentVO[] segmentVOs = lookupComponent.findSegmentVOByContractNumber(contractNumber, false, null);

        List segmentPKList = new ArrayList();

        segmentPKList.add(new Long(segmentVOs[0].getSegmentPK()));

        contract.business.Lookup contractLookup = new contract.component.LookupComponent();

        SegmentVO[] riderSegmentVOs = contractLookup.findRiderSegmentsBy_SegmentPK(segmentVOs[0].getSegmentPK());
        if (riderSegmentVOs != null && riderSegmentVOs.length > 0)
        {
            for (int i = 0; i < riderSegmentVOs.length; i++)
            {
                segmentPKList.add(new Long(riderSegmentVOs[i].getSegmentPK()));
            }
        }

        EDITTrxVO[] editTrxVOs =
                eventComponent.composeEDITTrxVOBySegmentPKs_AND_PendingStatus(segmentPKList, new String[] {"P"}, voInclusionList);

        return Util.marshalVOs(editTrxVOs);
    }

    /**
     * @see  extension.business.InduaseAdapter#retrieveAllTransactionHistoryForContract
     */
    public String[] retrieveAllTransactionHistoryForContract(String contractNumber) throws Throwable
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        List voInclusionList = new ArrayList();

        contract.business.Lookup lookupComponent = new contract.component.LookupComponent();

        SegmentVO[] segmentVOs = lookupComponent.findSegmentVOByContractNumber(contractNumber, false, null);

        EDITTrxHistoryVO[] editTrxHistoryVOs =
                eventComponent.composeEDITTrxHistoryVOBySegmentPK(segmentVOs[0].getSegmentPK(), voInclusionList);

        return Util.marshalVOs(editTrxHistoryVOs);
    }

    /**
     * @see extension.business.InduaseAdapter#retrieveAllChangeHistoryForContract
     */
    public String[] retrieveAllChangeHistoryForContract(String contractNumber) throws Throwable
    {
        contract.business.Lookup lookup = new contract.component.LookupComponent();

        List voInclusionList = new ArrayList();
        voInclusionList.add(ChangeHistoryVO.class);

        ChangeHistoryVO[] changeHistoryVOs = lookup.getAllChangeHistoryByContractNumber(contractNumber);

        return Util.marshalVOs(changeHistoryVOs);
    }

    /**
     * @see  extension.business.InduaseAdapter#retrieveAllSuspense
     */
    public String[] retrieveAllSuspense() throws Throwable
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        List voInclusionList = new ArrayList();

        SuspenseVO[] suspenseVOs = eventComponent.composeSuspenseVO(voInclusionList);

        return Util.marshalVOs(suspenseVOs);
    }

    /**
     * @see  extension.business.InduaseAdapter#retrieveAllMastersOfGroupType
     */
//    public String[] retrieveAllMastersOfGroupType(String groupTypeCT) throws Throwable
//    {
//        contract.business.Lookup lookupComponent = new contract.component.LookupComponent();
//
//        MasterVO[] masterVOs = lookupComponent.findMasterByGroupType(groupTypeCT);
//
//        return Util.marshalVOs(masterVOs);
//    }

    /**
     * @see  extension.business.InduaseAdapter#retrievePolicy
     */
//    public String retrievePolicy(String contractNumber) throws Throwable
//    {
//        List voInclusionList = new ArrayList();
//        voInclusionList.add(SegmentVO.class);
//        voInclusionList.add(MasterVO.class);
//        voInclusionList.add(BillingVO.class);
//        voInclusionList.add(BillLapseVO.class);
//        voInclusionList.add(PayoutVO.class);
//        voInclusionList.add(NoteReminderVO.class);
//        voInclusionList.add(ContractClientVO.class);
//
//        contract.business.Lookup lookupComponent = new contract.component.LookupComponent();
//
//        SegmentVO segmentVO = lookupComponent.composeSegmentVO(contractNumber, voInclusionList);
//
//        return Util.marshalVO(segmentVO);
//    }

    /**
     * @see  extension.business.InduaseAdapter#retrieveSuspenseByUserDefNumber
     */
    public String[] retrieveSuspenseByUserDefNumber(String userDefNumber) throws Throwable
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        List voInclusionList = new ArrayList();

        SuspenseVO[] suspenseVOs = eventComponent.composeSuspenseVOByUserDefNumber(userDefNumber, voInclusionList);

        return Util.marshalVOs(suspenseVOs);
    }

    /**
     * @see  extension.business.InduaseAdapter#retrieveSuspenseByPK
     */
    public String retrieveSuspenseByPK(String suspensePK) throws Throwable
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        List voInclusionList = new ArrayList();

        SuspenseVO suspenseVO = eventComponent.composeSuspenseVO(Long.parseLong(suspensePK), voInclusionList);

        return Util.marshalVO(suspenseVO);
    }

    /**
     * @see  extension.business.InduaseAdapter#getCodeTableDescription
     */
    public String getCodeTableDescription(String codeTableName, String code)
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        return codeTableWrapper.getCodeDescByCodeTableNameAndCode(codeTableName, code);
    }

    /**
     * @see  extension.business.InduaseAdapter#getCodeTableEntries
     */
    public String[] getCodeTableEntries(String codeTableName) throws Throwable
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        CodeTableVO[] codeTableVOs = codeTableWrapper.getCodeTableEntries(codeTableName);

        return Util.marshalVOs(codeTableVOs);
    }


    //  Methods required due to inheritance from AbstractComponent.  Do not use these methods.  The poolName is not
    //  known because this component provides access to various locations in the system.
    public int deleteVO(Class voClass, long primaryKey, boolean recursively)
    {
//        return super.deleteVO(voClass, primaryKey, poolName, false);
        return 0;
    }

    public Object retrieveVO(Class voClass, long primaryKey, boolean recursively, List voInclusionList)
    {
//        return super.retrieveVO(voClass, primaryKey, poolName, false, null);
        return null;
    }

    public long createOrUpdateVO(Object voObject, boolean recursively) throws Exception
    {
//        return super.createOrUpdateVO(voObject, poolName, true);
        return 0;
    }





    //  Private Methods


    /**
     * Sets default values for a segmentVO
     *
     * @param segmentVO
     *
     * @return
     *
     * @throws Exception
     */
    private SegmentVO setSegmentDefaults(SegmentVO segmentVO) throws Exception
    {
        EDITDate currentDate = new EDITDate();

        segmentVO.setTerminationDate(EDITDate.DEFAULT_MAX_DATE);
        segmentVO.setStatusChangeDate(null);
        segmentVO.setCreationDate(currentDate.getFormattedDate());
        segmentVO.setIssueDate(currentDate.getFormattedDate());
        segmentVO.setLastAnniversaryDate(segmentVO.getEffectiveDate());

        setIssueAgeForContractClients(segmentVO);

        return segmentVO;
    }

    /**
     * Determines the issue age and sets it for all contractClients within the given segmentVO
     *
     * @param segmentVO
     *
     * @throws Exception
     */
    private void setIssueAgeForContractClients(SegmentVO segmentVO) throws Exception
    {
        String effectiveDate = null;

        effectiveDate = segmentVO.getEffectiveDate();

        Contract contractComponent = new ContractComponent();

        ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();

        for (int i = 0; i < contractClientVOs.length; i++)
        {
            ContractClientVO contractClientVO = contractClientVOs[i];

            long clientRolePK = contractClientVO.getClientRoleFK();

            ClientRoleVO clientRoleVO = searchForRole(clientRolePK);

            long clientDetailPK = clientRoleVO.getClientDetailFK();

            ClientDetailVO clientDetailVO = searchForClient(clientDetailPK);

            String birthDate = clientDetailVO.getBirthDate();

            int issueAge = contractComponent.calculateIssueAgeForLifeContracts(birthDate, effectiveDate);

            contractClientVO.setIssueAge(issueAge);

            segmentVO.addContractClientVO(contractClientVO);
        }
    }

    /**
     * Finds a unqiue client via the supplied id.
     *
     * @param clientDetailPK
     *
     * @return
     *
     * @throws Exception
     */
    private ClientDetailVO searchForClient(long clientDetailPK) throws Exception
    {
        ClientDetailVO clientDetailVO = null;

        client.business.Lookup clientLookup = new client.component.LookupComponent();

        ClientDetailVO[] clientDetailVOs = clientLookup.findByClientPK(clientDetailPK, false, null);

        if (clientDetailVOs != null)
        {
            clientDetailVO = clientDetailVOs[0];
        }

        return clientDetailVO;
    }

    private ClientRoleVO searchForRole(long clientRolePK) throws Exception
    {
        ClientRoleVO clientRoleVO = null;

        role.business.Lookup roleLookup = new role.component.LookupComponent();

        clientRoleVO = roleLookup.getRoleByClientRolePK(clientRolePK)[0];

        return clientRoleVO;
    }

    /**
     * Calculates the person's age when the policy is issued.
     *
     * @param birthDate         date of birth
     * @param effectiveDate     date when the policy is issued
     *
     * @return age of the person
     *
     * @throws Exception
     */
//    private int calculateIssueAge(String birthDate, String effectiveDate) throws Exception
//    {
//        EDITDate ceEffDate = new EDITDate(effectiveDate);
//        EDITDate ceDOB = new EDITDate(birthDate);
//        int issueAge = ceEffDate.getAge(ceDOB, "last");
//
//        return issueAge;
//    }

    /**
     * Overlays the EDITTrxVO with default values depending on whether they are already set or not
     *
     * @param editTrxVO     object whose values are overlayed
     *
     * @throws Exception
     */
    private void overlayEDITTrxVO(EDITTrxVO editTrxVO) throws Exception
    {
//        if ((editTrxVO.getTaxYear() == null) || editTrxVO.getTaxYear().equals(""))
        if (editTrxVO.getTaxYear() == 0)
        {
            EDITDate currentDate = new EDITDate();

            editTrxVO.setTaxYear(currentDate.getYear());
        }

        if (editTrxVO.getNoAccountingInd().equals(""))
        {
            editTrxVO.setNoAccountingInd(null);
        }

        if (editTrxVO.getNoCommissionInd().equals(""))
        {
            editTrxVO.setNoCommissionInd(null);
        }

        if (editTrxVO.getZeroLoadInd().equals(""))
        {
            editTrxVO.setZeroLoadInd(null);
        }

        if (editTrxVO.getNoCorrespondenceInd().equals(""))
        {
            editTrxVO.setNoCorrespondenceInd(null);
        }

        editTrxVO.setMaintDateTime(this.defaultMaintDateTime());

        if ((editTrxVO.getOperator() == null) || (editTrxVO.getOperator().equalsIgnoreCase("")))
        {
            editTrxVO.setOperator(this.defaultOperator());
        }
    }

    /**
     * Overlays the GroupSetupVO with default values depending on whether they are already set or not
     *
     * @param groupSetupVO     object whose values are overlayed
     *
     * @throws Exception
     */
    private void overlayGroupSetupVO(GroupSetupVO groupSetupVO) throws Exception
    {
        groupSetupVO.setGroupTypeCT("");
        groupSetupVO.setGroupKey("");
    }

    /**
     * Attaches EDITTrxCorrespondence to the EDITTrx if required based on transaction type
     *
     * @param editTrxVO     object to add correspondence to
     *
     * @throws Exception
     */
    private void attachCorrespondence(EDITTrxVO editTrxVO) throws Exception
    {
        //  NOTE: this method does not use the EDITTrxCorrespondence entity object because it was not complete at the time

        TransactionCorrespondenceVO[] transactionCorrespondenceVO =
                event.dm.dao.DAOFactory.getTransactionCorrespondenceDAO().findByTransactionType(editTrxVO.getTransactionTypeCT());


        if (transactionCorrespondenceVO != null)
        {
            EDITDate correspondenceDate = calculateCorrespondenceDate(editTrxVO.getEffectiveDate(),
                    transactionCorrespondenceVO[0].getPriorPostCT(), transactionCorrespondenceVO[0].getNumberOfDays());

            EDITTrxCorrespondenceVO editTrxCorrespondenceVO = new EDITTrxCorrespondenceVO();

            editTrxCorrespondenceVO.setEDITTrxCorrespondencePK(0);
            editTrxCorrespondenceVO.setEDITTrxFK(0);
            editTrxCorrespondenceVO.setCorrespondenceDate(correspondenceDate.getFormattedDate());
            editTrxCorrespondenceVO.setStatus("P");
            editTrxCorrespondenceVO.setTransactionCorrespondenceFK(transactionCorrespondenceVO[0].getTransactionCorrespondencePK());

            editTrxVO.addEDITTrxCorrespondenceVO(editTrxCorrespondenceVO);
        }
    }

    /**
     * Calculates the correspondence date.  Adds or subtracts the numberOfDays from the effectiveDate depending on
     * whether the priorPostCT is 'prior' or 'post'.
     *
     * @param effectiveDate         date to modify with numberOfDays
     * @param priorPostCT           code table value says whether the correspondence is prior or post
     * @param numberOfDays          number of days to be added or subtracted
     *
     * @return correspondenceDate   calculated date
     */
    private EDITDate calculateCorrespondenceDate(String effectiveDate, String priorPostCT, int numberOfDays)
    {
        EDITDate correspondenceDate = new EDITDate(effectiveDate);

        if (priorPostCT.equals("Prior"))
        {
            correspondenceDate = correspondenceDate.subtractDays(numberOfDays);
        }
        else if (priorPostCT.equals("Post"))
        {
            correspondenceDate = correspondenceDate.addDays(numberOfDays);
        }

        return correspondenceDate;
    }

    /**
     * Composes the base SegmentVO for a given contract number
     *
     * @param contractNumber    contractNumber of Segment
     *
     * @return base SegmentVO
     *
     * @throws Exception
     */
    private SegmentVO composeBaseSegmentVOByContractNumber(String contractNumber) throws Exception
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(SegmentVO.class);
        voInclusionList.add(PayoutVO.class);
        voInclusionList.add(NoteReminderVO.class);
        voInclusionList.add(AgentHierarchyVO.class);
        voInclusionList.add(ContractClientVO.class);
        voInclusionList.add(ContractClientAllocationVO.class);
        voInclusionList.add(DepositsVO.class);
        voInclusionList.add(InvestmentVO.class);
        voInclusionList.add(InvestmentAllocationVO.class);

        contract.business.Lookup lookupComponent = new contract.component.LookupComponent();

//        SegmentVO[] segmentVOs = lookupComponent.findSegmentVOByContractNumber(contractNumber, false, null);
        SegmentVO segmentVO = lookupComponent.composeSegmentVO(contractNumber, voInclusionList);

        return segmentVO;
    }

    /**
     * Finds the SuspenseVO for a given userDefNumber
     *
     * @param userDefNumber     userDefNumber of the Suspense to be found
     *
     * @return single SuspenseVO
     *
     * @throws Exception
     */
    private SuspenseVO findSuspenseVOByUserDefNumber(String userDefNumber) throws Exception
    {
        event.business.Event eventComponent = new event.component.EventComponent();

        SuspenseVO[] suspenseVOs = eventComponent.findSuspenseByUserDefNumber(userDefNumber);

        return suspenseVOs[0];
    }

    /**
     * Returns the default maintenance date and time
     *
     * @return
     */
    private String defaultMaintDateTime()
    {
        return new EDITDateTime().getFormattedDateTime();
    }

    /**
     * Returns the default operator
     *
     * @return
     */
    private String defaultOperator()
    {
        return "System";
    }

    /**
     * Sets default values for ClientDetailVO
     * @param clientDetailVO
     */
    private void setClientDetailVODefaults(ClientDetailVO clientDetailVO)
    {
        clientDetailVO.setClientDetailPK(0);    // override their pk setting

        clientDetailVO.setMaintDateTime(defaultMaintDateTime());

        if ((clientDetailVO.getOperator() == null) || (clientDetailVO.getOperator().equalsIgnoreCase("")))
        {
            clientDetailVO.setOperator(defaultOperator());
        }
    }

    /**
     * Sets default values for SuspenseVO
     *
     * @param suspenseVO
     */
    private void setSuspenseVODefaults(SuspenseVO suspenseVO)
    {
        suspenseVO.setMaintDateTime(this.defaultMaintDateTime());

        if ((suspenseVO.getOperator() == null) || (suspenseVO.getOperator().equalsIgnoreCase("")))
        {
            suspenseVO.setOperator(this.defaultOperator());
        }
    }

    /**
     * Converts an InduaseContractClientVO to a ContractClient with the appropriate billing vo structure for Payors
     *
     * The InduaseContractClientVO was created to provide a way to send in ContractClient information with a BillLapse
     * and Billing (Billing is a parent of BillLapse so no way to provide its info in the normal VO structure).  This
     * method adds the Billing to the BillLapse and adds the BillLapse to the ContractClient if it was a Payor
     *
     * @param induaseContractClientVO               specialized VO containing the ContractClient and Billing
     * @param policyGroup                           policyGroup needed for determining groupType and getting Billing from
     *                                                  database is the groupType is GROUP
     *
     * @return ContractClientVO properly modified for billing
     *
     * @throws Exception
     */
    private ContractClientVO convertToContractClient(InduaseContractClientVO induaseContractClientVO)
            throws Exception
    {
        ContractClientVO contractClientVO = induaseContractClientVO.getContractClientVO();

        if (contractClientVO != null)
        {
            if (isPayor(contractClientVO))
            {
//                BillLapseVO[] billLapseVOs = contractClientVO.getBillLapseVO();

                //  Get the Billing from the induaseContractClientVO for policyGroups of type
                //  Individual (policyGroup == null) or List
//                BillingVO billingVO = induaseContractClientVO.getBillingVO();

//                if (policyGroup != null)
//                {
//                    if (policyGroup.getGroupTypeCT().equalsIgnoreCase("GROUP"))
//                    {
//                        //  For policyGroups of type Group, get the Billing from the database
//                        contract.business.Lookup contractlookupComponent = new contract.component.LookupComponent();
//                        billingVO = contractlookupComponent.findBillingByPolicyGroupPK(policyGroup.getPolicyGroupPK().longValue())[0];
//                    }
//                }

//                billLapseVOs[0].setParentVO(BillingVO.class, billingVO);
//                billLapseVOs[0].setBillingFK(billingVO.getBillingPK());

//                contractClientVO.setBillLapseVO(billLapseVOs);
            }

            overlayContractClientData(contractClientVO);
        }

        return contractClientVO;
    }

    /**
     * Sets appropriate defaults for ContractClient and its ContractClientAllocations
     *
     * @param contractClientVO
     *
     * @throws Exception
     */
    private void overlayContractClientData(ContractClientVO contractClientVO) throws Exception
    {
        //  Get the ClientRoleVO via its pk
        role.business.Lookup roleLookup = new role.component.LookupComponent();
        ClientRoleVO[] clientRoleVOs = roleLookup.findClientRoleVOByClientRolePK(contractClientVO.getClientRoleFK(), false, null);

        //  Find ClientDetail
        client.business.Lookup clientLookupComponent = new client.component.LookupComponent();
        ClientDetailVO[] clientDetailVOs = clientLookupComponent.findByClientPK(clientRoleVOs[0].getClientDetailFK(), false, null);

        //  Overlay ContractClientVO
        contractClientVO.setContractClientPK(0);        // set to zero for save (this is new)
        contractClientVO.setOverrideStatus("P");

        Contract contractComponent = new ContractComponent();
        contractClientVO.setIssueAge(contractComponent.calculateIssueAge(clientDetailVOs[0].getBirthDate(), new EDITDate().getFormattedDate()));

        //  Overlay ContractClientAllocation if exists
        ContractClientAllocationVO[] contractClientAllocationVOs = contractClientVO.getContractClientAllocationVO();

        for (int i = 0; i < contractClientVO.getContractClientAllocationVOCount(); i++)
        {
            contractClientAllocationVOs[i].setOverrideStatus("P");
        }
    }

    /**
     * Determines if the ContractClient has a role of type Payor
     *
     * @param contractClientVO
     *
     * @return  true if Payor, false otherwise
     *
     * @throws Exception
     */
    private boolean isPayor(ContractClientVO contractClientVO) throws Exception
    {
        role.business.Lookup lookupComponent = new role.component.LookupComponent();
        ClientRoleVO[] clientRoleVOs = lookupComponent.findClientRoleVOByClientRolePK(contractClientVO.getClientRoleFK(), false, null);

        String roleType = clientRoleVOs[0].getRoleTypeCT();

        if (roleType.equalsIgnoreCase("POR"))
        {
            return true;
        }

        return false;
    }
}
