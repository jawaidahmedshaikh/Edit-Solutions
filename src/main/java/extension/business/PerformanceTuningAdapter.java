/*
 * User: dlataill
 * Date: May 20, 2004
 * Time: 7:30:58 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package extension.business;

import edit.services.component.ICRUD;
import edit.common.vo.*;

/**
 * A specialized interface for the Induase company to access the EDITSolutions system via their own user interface.
 *
 * This class does not expect the caller to supply primary and foreign key values.  If the keys are provided, they are
 * ignored.
 */
public interface PerformanceTuningAdapter extends ICRUD
{
    /**
     * Adds a client to the database
     *
     * @param clientDetailAsXML                 XML representation of ClientDetailVO
     *
     * @return primaryKey of saved clientDetail
     *
     * @throws Throwable
     */
    public String addClient(String clientDetailAsXML) throws Throwable;

    /**
     * Adds a clientRole to the database
     *
     * @param clientIdentification            identifier of an existing client (ClientDetail)
     * @param clientRoleAsXML                 XML representation of ClientRoleVO
     *
     * @return primaryKey of saved clientRole
     *
     * @throws Throwable
     */
    public String addClientRole(String clientIdentification, String clientRoleAsXML) throws Throwable;

    /**
     * Adds a ContractClient to an existing contract and an existing ClientRole.  The clientRoleFK field (foreign key
     * of the existing ClientRole) must be supplied in the ContractClient.
     * <P>
     * The specialized InduaseContractClientVO is used to provide a way to get Billing for a ContractClient's
     * BillLapse.
     * <P>
     * If the ContractClient is a Payor, the ContractClient is expected to have a BillLapse attached to it.  The Billing
     * is in the InduaseContractClient (unless the Segment's Master is of type Group, then Billing would already be in
     * the database).  If the billing information is not included with the ContractClient, the ContractClient will be
     * saved without saving billing.
     * <P>
     * NOTE:  ContractClients must have the appropriate clientRoleFK completed
     *
     * @param contractNumber                    contract number of existing Segment
     * @param induaseContractClientAsXML        XML representation of InduaseContractClientVO
     *
     * @return primary key of newly created ContractClient
     *
     * @throws Throwable
     */
    public String addContractClientToExistingContractAndClientRole(String contractNumber, String induaseContractClientAsXML)
            throws Throwable;

    /**
     * Adds a suspense to the database
     *
     * @param suspenseAsXML                     XML representation of SuspenseVO
     *
     * @return primaryKey of saved suspense
     *
     * @throws Throwable
     */
    public String addSuspense(String suspenseAsXML) throws Throwable;

    /**
     * Saves a segment to the database and associates it with the given master.  If no masterNumber is provided,
     * it is assumed to be an individual policy (i.e. an IndividualMaster is automatically created).
     * <P>
     * The segment is saved recursively so all of its children are saved too (excepted for ContractClients)
     * <P>
     * An array of InduaseContractClients must be provided to have ContractClients included in the policy.  If
     * ContractClients are added to the Segment, they will be ignored.  The specialized InduaseContractClientVO is
     * used to provide a way to get Billing attached to the ContractClient's BillLapse.   NOTE:  ContractClients must
     * have the appropriate clientRoleFK completed and any necessary BillLapse and Billing for Payors.  See
     * documentation for addContractClientToExistingContractAndClientRole() method for more information.
     *
     * @param segmentAsXML                      XML representation of SegmentVO
     * @param caseNumber                        identifier of the PolicyGroup
     * @param companyStructureAsXML             XML representation of ProductStructureVO
     * @param induaseContractClientsAsXML       XML representation of an array of InduaseContractClientVOs
     *
     * @return primaryKey of saved segment
     *
     * @throws Throwable
     *
     * @see    PerformanceTuningAdapter#addContractClientToExistingContractAndClientRole
     */
    public String addPolicy(String segmentAsXML, String caseNumber, String companyStructureAsXML,
                            String[] induaseContractClientsAsXML) throws Throwable;


    public String addProductStructure(String ProductStructureAsXML) throws Throwable;

    /**
     * Deletes a client from the database via the client identification
     *
     * @param clientIdentification              id of client to be deleted
     *
     * @throws Throwable
     */
    public void deleteClient(String clientIdentification) throws Throwable;

    /**
     * Deletes a suspense from the database
     *
     * @param suspensePK                        the primary key of the Suspense to be deleted
     *
     * @throws Throwable
     */
    public void deleteSuspense(String suspensePK) throws Throwable;

    /**
     * Updates the contents of a client in the database.  Assumes that the PK is not provided and finds it via the
     * clientID
     *
     * @param clientDetailAsXML                 XML representation of ClientDetailVO
     *
     * @throws Throwable
     */
    public void updateClient(String clientDetailAsXML) throws Throwable;

    /**
     * Updates the contents of a suspense in the database. Uses the primary key to identify the Suspense to be
     * updated
     *
     * @param suspenseAsXML                     XML representation of SuspenseVO
     *
     * @throws Throwable
     */
    public void updateSuspense(String suspenseAsXML) throws Throwable;

    /**
     * Updates the contents of a policy (segment) in the database.  The Segment may contain any children and they will
     * also be saved (as is).  The segment primary key, the policyGroup foreign key, and the companyStructure foreign
     * key are overlayed with the correct values from the database if not provided in input.
     *
     * @param segmentAsXML                      XML representation of SegmentVO
     *
     * @throws Throwable
     */
    public void updatePolicy(String segmentAsXML) throws Throwable;

    /**
     * Saves a Master to the database as a GroupMaster.  A Billing must be provided since there is only 1 Billing
     * for a Group Master.
     *
     * @param masterAsXML                       XML representation of MasterVO
     * @param billingAsXML                      XML representation of BillingVO
     *
     * @throws Throwable
     */
//    public void saveGroupMaster(String masterAsXML, String billingAsXML) throws Throwable;

    /**
     * Saves a Master to the database as a ListMaster.  Does not save any children.
     *
     * @param masterAsXML                       XML representation of MasterVO
     *
     * @throws Throwable
     */
//    public void saveListMaster(String masterAsXML) throws Throwable;

    /**
     * Attempts to issue a previously saved policy.  Generates transactions which are stored in the db and processed
     * if the issueDate is back-dated.
     *
     * @param contractNumber                    contractNumber of Segment
     * @param issueDate                         date the contract will be issued.  If back-dated, the transactions
     *                                              will be processed in real-time
     * @param operator                          name of operator issuing the process.  If not provided, defaults to
     *                                              "System"
     * @param lastDayOfMonthInd                 last day of month indicator.  If 'Y', will use the last day of the
     *                                              month.  If not provided, defaults to 'N'
     *
     * @throws Throwable
     */
    public void issueNewBusiness(String contractNumber, String issueDate, String operator, String lastDayOfMonthInd) throws Throwable;


    /**
     * Processes an inforce quote and returns the results
     *
     * @param contractNumber                    contractNumber of Segment
     * @param quoteDate                         date of quote (must be >= segment's effective date to be processed)
     *
     * @return XML representation of QuoteVO
     *
     * @throws Throwable
     */
    public String processInforceQuote(String contractNumber, String quoteDate) throws Throwable;

    /**
     * Saves a transaction to the database and processes it if back-dated.
     * <P>
     * The GroupSetup must also supply ContractSetup and OutSuspense as its children.  It must also supply ClientSetup
     * as its children if client overrides are desired.
     *
     * @param contractNumber                    contractNumber of Segment
     * @param groupSetupAsXML                   XML representation of GroupSetupVO.  The following fields are expected:
     * <UL>                                         PremiumTypeCT 	    (only if a Premium)
     *   <BR>                                       GrossNetStatusCT 	(only if a Withdrawal)
     *   <BR>                                       DistributionCodeCT  (only if Withdrawal, Surrender, some type of removal transaction)
     *   <BR>                                       WithdrawalTypeCT 	(only if Withdrawal)
     *   <BR>                                       GroupAmount
     *   <BR>                                       EmployerContribution
     *   <BR>                                       EmployeeContribution
     *   <BR>                                       ContractSetup (children)
     *   <UL>                                           PolicyAmount
     *     <BR>                                         DeathStatusCT   (only for death)
     *     <BR>                                         CostBasis
     *     <BR>                                         AmountReceived
     *     <BR>                                         ClaimStatusCT   (only for claim)
     *   </UL>                                      OutSuspense (children)
     *   <UL>                                           SuspenseFK
     *     <BR>                                         Amount
     *   </UL>                                      ClientSetup (children if have client overrides)
     *   <UL>                                           ContractClientFK
     *     <BR>                                         ClientRoleFK
     *   </UL>
     * </UL>
     *
     * @param editTrxAsXML                      XML representation of EDITTrxVO.  The following fields are expected:
     * <UL>                                         EffectiveDate
     *   <BR>                                       PendingStatus
     *   <BR>                                       SequenceNumber
     *   <BR>                                       TaxYear             (if null, will set to current year)
     *   <BR>                                       TrxAmount
     *   <BR>                                       DueDate             (if scheduledEvent)
     *   <BR>                                       TransactionTypeCT
     *   <BR>                                       TrxIsRescheduledInd
     *   <BR>                                       NoCorrespondenceInd
     *   <BR>                                       NoAccountingInd
     *   <BR>                                       NoCommissionInd
     *   <BR>                                       ZeroLoadInd
     *   <BR>                                       MaintDateTime       (if not set, will set to current date and time)
     *   <BR>                                       Operator            (if not set, will default to System)
     * </UL>
     *
     * @throws Throwable
     */
    public void processTransaction(String contractNumber, String groupSetupAsXML, String editTrxAsXML) throws Throwable;

    /**
     * Deletes a transaction and any of its setups if able (depending on whether history or scheduled events already
     * exist)
     *
     * @param editTrxPK                             primary key of the EDITTrx to be deleted
     *
     * @throws Throwable
     */
    public void deleteTransaction(String editTrxPK) throws Throwable;

    /**
     * Updates an existing transaction in the database and processes it if back-dated
     *
     * @see   PerformanceTuningAdapter#processTransaction
     *
     * @param contractNumber                        see processTransaction
     * @param groupSetupAsXML                       see processTransaction
     * @param editTrxAsXML                          see processTransaction
     *
     * @throws Throwable
     */
    public void updateTransaction(String contractNumber, String groupSetupAsXML, String editTrxAsXML) throws Throwable;

    /**
     * Reverses a history record.  This history could be non-financial (ChangeHistory) or financial (EDITTrx).
     *
     * @param contractNumber                        identifier of the contract to be modified
     * @param historyPK                             primary key of the item to be reversed.  This is the pk of the
     *                                                  ChangeHistory record or the EDITTrx record, depending on
     *                                                  historyType.
     * @param historyType                           type of item to be reversed (i.e. "ChangeHistory" for non-financial
     *                                                  histories, null or anything else for EDITTrx histories)
     *
     * @throws Exception
     */
    public void reverseHistory(String contractNumber, String historyPK, String historyType) throws Exception;

    /**
     * Finds all transactions that are pending
     *
     * @return XML representation of an array of EDITTrxVOs
     *
     * @throws Throwable
     */
    public String[] retrieveAllPendingTransactions() throws Throwable;

    /**
     * Finds all transactions that are pending for a given contract number
     *
     * @param contractNumber                    contractNumber of Segment
     *
     * @return XML representation of an array of EDITTrxVOs
     *
     * @throws Throwable
     */
    public String[] retrieveAllPendingTransactionsForContract(String contractNumber) throws Throwable;

    /**
     * Finds all transaction history for a given contract number
     *
     * @param contractNumber                    contractNumber of Segment
     *
     * @return XML representation of an array of EDITTrxHistoryVOs
     *
     * @throws Throwable
     */
    public String[] retrieveAllTransactionHistoryForContract(String contractNumber) throws Throwable;

    /**
     * Finds all the change histories for a given contract number
     *
     * @param contractNumber                    contractNumber of Segment
     *
     * @return XML representation of an array of ChangeHistoryVOs
     *
     * @throws Throwable
     */
    public String[] retrieveAllChangeHistoryForContract(String contractNumber) throws Throwable;

    /**
     * Finds all suspense
     *
     * @return XML representation of array of SuspenseVOs
     *
     * @throws Throwable
     */
    public String[] retrieveAllSuspense() throws Throwable;

    /**
     * Finds all Masters with the specified groupType
     *
     * @param groupTypeCT                         groupTypeCT field of Masters to be retrieved
     *
     * @return XML representation of array of MasterVOs
     *
     * @throws Throwable
     */
//    public String[] retrieveAllMastersOfGroupType(String groupTypeCT) throws Throwable;

     /**
     * Finds Segment, Master, Billing, BillLapse, Payout, NoteReminder, ContractClients and riders for a given
      * contractNumber
      *
     * @param contractNumber                    contractNumber of Segment
      *
     * @return XML representation of a MasterVO with attached SegmentVOs, PayoutVOs, NoteReminderVOs, ContractClientVOs,
      *         BillingVOs, and BillLapseVOs
      *
     * @throws Throwable
     */
//    public String retrievePolicy(String contractNumber) throws Throwable;

    /**
     * Finds all the suspense for a given userDefNumber
     *
     * @param userDefNumber                    userDefNumber field of the Suspense
     *
     * @return XML representation of array of SuspenseVOs
     *
     * @throws Throwable
     */
    public String[] retrieveSuspenseByUserDefNumber(String userDefNumber) throws Throwable;

    /**
     * Finds a the Suspense specified by the primary key
     *
     * @param suspensePK                        primary key of the Suspense to be retrieved
     *
     * @return XML representation of a SuspenseVO
     *
     * @throws Throwable
     */
    public String retrieveSuspenseByPK(String suspensePK) throws Throwable;

    /**
     * Returns the full description of a given code table entry
     *
     * @param codeTableName                     value of the CodeTableName field in the CodeTableDef database table
     * @param code                              value of the Code field in the CodeTable database table
     *
     * @return description
     */
    public String getCodeTableDescription(String codeTableName, String code);

    /**
     * Gets the code table values for a given code table name
     *
     * @param codeTableName                     value of the CodeTableName field in the CodeTableDef database table
     *
     * @return XML representation of an array of CodeTableVOs
     *
     * @throws Throwable
     */
    public String[] getCodeTableEntries(String codeTableName) throws Throwable;

    /**
     * Create and save Premium trx
     * @param contractNumber
     * @param policyAmount
     * @param effectiveDate
     * @return
     * @throws Throwable
     */
    public String addPremiumTrx(String contractNumber, String policyAmount, String effectiveDate) throws Throwable;

   /**
     * Create and save Surrender trx
     * @param contractNumber
     * @param policyAmount
     * @param effectiveDate
     * @return
     * @throws Throwable
     */
    public String addSurrenderTrx(String contractNumber, String policyAmount, String effectiveDate) throws Throwable;

    /**
     * Create and save Transfer trx
     * @param contractNumber
     * @param policyAmount
     * @param effectiveDate
     * @return
     * @throws Throwable
     */
    public String addTransferTrx(String contractNumber, String policyAmount, String effectiveDate) throws Throwable;

    public String addWithdrawalTrx(String contractNumber, String policyAmount, String effectiveDate) throws Throwable;

    public String addDeathTrx(String contractNumber, String policyAmount, String effectiveDate) throws Throwable;

}
