/*
 * User: cgleason
 * Date: Jul 26, 2005
 * Time: 8:25:12 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.transaction;

import casetracking.usecase.SaveAfterScriptProcessing;

import client.ClientDetail;

import codetable.TrxProcessDocforPerformance;

import contract.Segment;

import edit.common.*;
import edit.common.exceptions.EDITEventException;
import edit.common.vo.AllowableTransaction;
import edit.common.vo.VOObject;
import edit.common.vo.ValidationVO;

import edit.services.config.ServicesConfig;
import edit.services.logging.Logging;

import engine.business.Calculator;

import engine.component.CalculatorComponent;

import engine.sp.SPException;
import engine.sp.SPOutput;

import event.*;

import event.financial.client.trx.ClientTrx;

import fission.utility.Util;

import java.util.Iterator;
import java.util.Set;

import logging.Log;
import logging.LogEvent;

import org.dom4j.Document;


public abstract class TransactionProcessor
{
    protected EDITTrx editTrx;
    private EDITDate cycleDate;
    private Set transactionCorrespondences;

    public TransactionProcessor()
    {

    }

    public TransactionProcessor(EDITTrx editTrx)
    {
        this.editTrx = editTrx;
    }

    /**
     * Transactions with dates less than the current date are executed real time
     *
     * @return
     */
    public boolean isBackdated()
    {
        if (editTrx.getEffectiveDate().before(new EDITDate()))
        {
            setCycleDate();
            return true;
        }
        else
        {
            return false;
        }
    }

    private void setCycleDate()
    {
        this.cycleDate = new EDITDate();
    }


    /**
     * Build the document for script processing
     *
     * @param clientTrx
     *
     * @return
     */
    public TrxProcessDocforPerformance buildDocument(ClientTrx clientTrx) throws EDITEventException
    {
        TrxProcessDocforPerformance trxProcessDocforPerformance = new TrxProcessDocforPerformance(clientTrx);

        trxProcessDocforPerformance.buildDocument();

        return trxProcessDocforPerformance;
    }

    /**
     * Build the document for script processing with clientTrx and editTrx
     * @param clientTrx
     * @param editTrx
     * @return
     */
    public TrxProcessDocforPerformance buildDocument(ClientTrx clientTrx, EDITTrx editTrx) throws EDITEventException
    {
        TrxProcessDocforPerformance trxProcessDocforPerformance = new TrxProcessDocforPerformance(clientTrx, editTrx);

        trxProcessDocforPerformance.buildDocument();

        return trxProcessDocforPerformance;
    }


    /**
     * Process the transaction through a script
     *
     * @param document
     * @param event
     * @param status
     * @param eventType
     * @param effectiveDate
     * @param productKey
     * @param errorProcessing
     *
     * @throws EDITEventException
     */
    public void executeTrx(Segment segment, String rootElementName, Document document, String event, String status, String eventType, String effectiveDate, long productKey, boolean errorProcessing) throws EDITEventException
    {
        Calculator calcComponent = new CalculatorComponent();
        SPOutput spOutput = null;

        try
        {
            spOutput = calcComponent.processScriptWithDocument(rootElementName, document, event, status, eventType, effectiveDate, productKey, errorProcessing);
        }
        catch (SPException e)
        {
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);

            EDITEventException editEventException = new EDITEventException(e.getMessage());

            ValidationVO[] validationVOs = e.getValidationVO();

            editEventException.setValidationVO(validationVOs);

            throw editEventException;
        }
        catch (RuntimeException e)
        {
            Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));

            Log.logGeneralExceptionToDatabase(null, e);

            EDITEventException editEventException = new EDITEventException(e.getMessage());

            throw editEventException;
        }

        VOObject[] voObjects = spOutput.getSPOutputVO().getVOObject();

        if (voObjects != null)
        {
            SaveAfterScriptProcessing saveAfterScriptProcessing = new SaveAfterScriptProcessing();

            saveAfterScriptProcessing.doUpdates(voObjects, editTrx, cycleDate, segment);
        }
    }

    /**
     * Check the config file for the Allowable transaction entries
     *
     * @param productStructurePK
     *
     * @return
     */
    public static boolean hasAllowableList(long productStructurePK)
    {
        AllowableTransaction[] allowableTransactions = ServicesConfig.getEDITDeathProcess(productStructurePK);
        boolean allowableTrxFound = false;

        if (allowableTransactions != null)
        {
            allowableTrxFound = true;
        }

        return allowableTrxFound;
    }

     /**
     * Checks the EDITServicesConfig.xml file for transactions that are allowed to process when the given contract
     * is in one of the "death" statuses.  If there are not any entries in the config file all transactions will be allowed to process.
     * @param segmentVO
     * @param transactionType
     * @param effectiveDate
     *
     * @return
     */
    public static boolean checkForAllowableTransaction(Segment segment,
                                                       String transactionType,
                                                       EDITDate effectiveDate,
                                                       String trxMode)
    {
        boolean allowableTrxFound = false;

        AllowableTransaction[] allowableTransactions = ServicesConfig.getEDITDeathProcess(segment.getProductStructureFK().longValue());

        if (allowableTransactions != null)
        {
            for (int i = 0; i < allowableTransactions.length; i++)
            {
                if (transactionType.equalsIgnoreCase(allowableTransactions[i].getTransactionType()))
                {
                    allowableTrxFound = true;

                    String dateRestriction = Util.initString(allowableTransactions[i].getDateRestriction(), "");
                    if (dateRestriction != null && !dateRestriction.equals(""))
                    {
                        String roleRestriction = allowableTransactions[i].getRoleRestriction();

                        if (roleRestriction != null)
                        {
                            ClientDetail clientDetail = ClientDetail.findBy_Segment_RoleType(segment, roleRestriction);
                            if (dateRestriction.equalsIgnoreCase("DateOfDeath"))
                            {
                                EDITDate dateOfDeath = clientDetail.getDateOfDeath();
                                allowableTrxFound = isAllowableDate(effectiveDate, trxMode, dateOfDeath);
                                break;
                            }
                            else if (dateRestriction.equalsIgnoreCase("ProofOfDeathReceivedDate"))
                            {
                                EDITDate proofOfDeathDate = clientDetail.getProofOfDeathReceivedDate();
                                allowableTrxFound = isAllowableDate(effectiveDate, trxMode, proofOfDeathDate);
                                break;
                            }
                            else
                            {
                                allowableTrxFound = false;
                            }
                        }
                        else
                        {
                            allowableTrxFound = false;
                        }
                    }
                }
            }
        }
        else
        {
            allowableTrxFound = true;
        }

        return allowableTrxFound;
    }

    /**
     * NOTE: OVERLOADED METHOD TO ADD NEW PROCESSING DEFICIENT IN THE CONFIG FILE
     * Checks the EDITServicesConfig.xml file for transactions that are allowed to process when the given contract
     * is in one of the "death" statuses.  If there are not any entries in the config file all transactions will be allowed to process.
     * @param segmentVO
     * @param transactionType
     * @param effectiveDate
     * @return
     */
    public static boolean checkForAllowableTransaction(Segment segment,
                                                       String transactionType, 
                                                       EDITDate effectiveDate,
                                                       String trxMode,
                                                       String originalTrxCode,
                                                       EDITDate originalTrxEffDate)
    {
        boolean allowableTrxFound = false;

        AllowableTransaction[] allowableTransactions = ServicesConfig.getEDITDeathProcess(segment.getProductStructureFK());

        if (allowableTransactions != null)
        {
            for (int i = 0; i < allowableTransactions.length; i++)
            {
                if (transactionType.equalsIgnoreCase(allowableTransactions[i].getTransactionType()))
                {
                    allowableTrxFound = true;

                    String dateRestriction = Util.initString(allowableTransactions[i].getDateRestriction(), "");
                    if (!originalTrxCode.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FULLSURRENDER))
                    {
                        if (dateRestriction != null && !dateRestriction.equals(""))
                        {
                            String roleRestriction = allowableTransactions[i].getRoleRestriction();

                            if (roleRestriction != null)
                            {
                                ClientDetail clientDetail = ClientDetail.findBy_Segment_RoleType(segment, roleRestriction);
                                if (dateRestriction.equalsIgnoreCase("DateOfDeath"))
                                {
                                    EDITDate dateOfDeath = clientDetail.getDateOfDeath();
                                    allowableTrxFound = isAllowableDate(effectiveDate, trxMode, dateOfDeath);
                                    break;
                                }
                                else if (dateRestriction.equalsIgnoreCase("ProofOfDeathReceivedDate"))
                                {
                                    EDITDate proofOfDeathDate = clientDetail.getProofOfDeathReceivedDate();
                                    allowableTrxFound = isAllowableDate(effectiveDate, trxMode, proofOfDeathDate);
                                    break;
                                }
                                else
                                {
                                    allowableTrxFound = false;
                                }
                            }
                            else
                            {
                                allowableTrxFound = false;
                            }
                        }
                    }
                    else
                    {
                        //For a Surrender
                        allowableTrxFound = isAllowableDate(effectiveDate, trxMode, originalTrxEffDate);
                    }
                }
            }
        }
        else
        {
            allowableTrxFound = true;
        }

        return allowableTrxFound;
    }

    protected static boolean isAllowableDate(EDITDate effectiveDate, String trxMode, EDITDate allowableDate)
    {
        boolean  allowableTrxFound = true;
        if (trxMode != null)
        {
           allowableDate = allowableDate.addMode(trxMode);
        }

        if (! (effectiveDate.before(allowableDate) || effectiveDate.equals(allowableDate)) )
        {
            allowableTrxFound = false;
        }

        return allowableTrxFound;
    }

    /**
     * Set the sequence number on the casetracking transaction
     * @param editTrx
     */
    public void setSequenceNumber(EDITTrx editTrx, Segment segment, int sequenceNumber)
    {
        EDITTrx maxEditTrx = EDITTrx.findMaxSequenceByEffectiveDate_TrxType(segment, editTrx.getEffectiveDate(), editTrx.getTransactionTypeCT());

        if(maxEditTrx == null)
        {
            editTrx.setSequenceNumber(sequenceNumber);
        }
        else
        {
            editTrx.setSequenceNumber(maxEditTrx.getSequenceNumber() + 1);
        }
    }

    /**
     * Determine if this TransactionType is to have commissions
     * @param editTrx
     * @return
     */
    public static boolean isCommissionableEvent(EDITTrx editTrx)
    {
        boolean isCommissionableEvent = false;

        TransactionPriority transactionPriority = TransactionPriority.findByTrxType(editTrx.getTransactionTypeCT());

        String commissionableEventInd = transactionPriority.getCommissionableEventInd();

        if (commissionableEventInd != null)
        {
            if (commissionableEventInd.equalsIgnoreCase("Y"))
            {
                editTrx.setCommissionStatus("P");
                
                isCommissionableEvent = true;
            }
        }

        return isCommissionableEvent;
    }

    /**
     * Determine if correspondence is associated with a transaction type
     * @param editTrx
     * @return
     */
    public boolean shouldSetupCorrespondence(EDITTrx editTrx)
    {
        boolean correspondenceRequired = false;
        boolean statusFieldsOK = false;

       // Does this TrxType even have correspondence associated with it (i.e. are there any TransactionCorrespondenceVO)?
        getTransactionCorrespondence(editTrx);

        if (transactionCorrespondences != null)
        {
            correspondenceRequired = true;
        }

        // Check the status' on the EDITTrx
        if (editTrx.getStatus().equals("N") &&
            editTrx.getPendingStatus().equals("P") &&
            editTrx.getNoCorrespondenceInd().equals("N"))
        {
            statusFieldsOK = true;
        }
        else
        {
            statusFieldsOK = false;
        }

        return correspondenceRequired && statusFieldsOK;
    }

    /**
     * For each TransactionCorrespondence create an EDITTrxCorrespondence
     * @param editTrx
     * @throws Exception
     */
    public void setupCorrespondence(EDITTrx editTrx)
    {

        for (Iterator iterator = transactionCorrespondences.iterator(); iterator.hasNext();)
        {
            TransactionCorrespondence transactionCorrespondence = (TransactionCorrespondence) iterator.next();

            EDITTrxCorrespondence editTrxCorrespondence = new EDITTrxCorrespondence();
            editTrxCorrespondence.setStatus("P");

            EDITDate correspondenceDate = editTrxCorrespondence.calcCorrespondenceDate(transactionCorrespondence,editTrx.getEffectiveDate());
            editTrxCorrespondence.setCorrespondenceDate(correspondenceDate);

            EDITDate trxEffectiveDate = editTrx.getEffectiveDate();

            editTrx.addEDITTrxCorrespondence(editTrxCorrespondence);
            transactionCorrespondence.addEDITTrxCorrespondence(editTrxCorrespondence);
        }
    }

    /**
     * Get all the TransactionCorrespondence associated with a transactionType
     * @param editTrx
     */
    private void getTransactionCorrespondence(EDITTrx editTrx)
    {
        //todo - NO 'TU' thru casetracking yet
//        String trxTypeQualifier = null;
//        if (editTrx.getTransactionTypeCT().equalsIgnoreCase("TU"))
//        {
//            trxTypeQualifier = editTrx.getTransferUnitsType();
//        }

        TransactionPriority transactionPriority = TransactionPriority.findByTrxType(editTrx.getTransactionTypeCT());

        TransactionCorrespondence transactionCorrespondence = null;

//        if (trxTypeQualifier != null)
//        {
//            transactionCorrespondenceVO =
//                    TransactionCorrespondence.findByTransactionPriorityPK_TrxTypeQualifier(transactionPriorityVO[0].getTransactionPriorityPK(), trxTypeQualifier);
//        }
//        else
//        {
            transactionCorrespondences =
                    TransactionCorrespondence.findByTransactionPriorityPK(transactionPriority.getTransactionPriorityPK());
//        }
    }


   /**
     * Returns true if this reinsurable transaction:
     * 1) Is reinsurable
     * 2) Has ContractTreaty information associated with it.
     * It is not necessary to call isReinsurable() before invoking this method as it would be redundant.
     * @return
     */
    public void hasReinsurance(EDITTrx editTrx, Segment segment)
    {
        boolean hasReinsurance = false;

        if (isReinsurable(editTrx))
        {
            hasReinsurance = (segment.getContractTreaty() != null);
        }

        if (hasReinsurance)
        {
            editTrx.setReinsuranceStatus("P");
        }
    }

  /**
     * Returns true if the transactionTypeCT has been configured as Reinsurable.
     * @param editTrx
     * @return
     */
    public boolean isReinsurable(EDITTrx editTrx)
    {
        TransactionPriority transactionPriority = TransactionPriority.findByTrxType(editTrx.getTransactionTypeCT());

        String reinsurableInd = transactionPriority.getReinsuranceInd();

        if (reinsurableInd != null)
        {
            if (reinsurableInd.equalsIgnoreCase("Y"))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * The following events must be checked, if the event exists the proper indicators and table entries are created.
     * Events = Commissions, Correspondence and Reinsurance
     * @param editTrx
     * @param segment
     */
    public void checkForEvents(EDITTrx editTrx, Segment segment)
    {
        isCommissionableEvent(editTrx);

        if (shouldSetupCorrespondence(editTrx))
        {
            setupCorrespondence(editTrx);
        }

        hasReinsurance(editTrx, segment);
    }

    //                          CONVENIENCE METHODS

    /**
     * Build a default EDITTrx
     *
     * @param transactionTypeCT
     * @param effectiveDate
     * @param taxYear
     * @param operator
     *
     * @return
     */
    public static EDITTrx buildDefaultEDITTrx(String transactionTypeCT, EDITDate effectiveDate, int taxYear, String operator)
    {
        EDITTrx editTrx = new EDITTrx();

        editTrx.setEffectiveDate(effectiveDate);
        editTrx.setStatus("N");
        editTrx.setPendingStatus("P");

        editTrx.setTaxYear(taxYear);
        editTrx.setTransactionTypeCT(transactionTypeCT);
        editTrx.setTrxIsRescheduledInd("N");
        editTrx.setCommissionStatus("N");
        editTrx.setLookBackInd("N");
        editTrx.setNoCorrespondenceInd("N");
        editTrx.setNoAccountingInd("N");
        editTrx.setNoCommissionInd("N");
        editTrx.setZeroLoadInd("N");
        editTrx.setMaintDateTime(new EDITDateTime());
        editTrx.setOperator(operator);
        editTrx.setPremiumDueCreatedIndicator("N");

        return editTrx;
    }

    /**
     * Build a default ContractSetup
     *
     * @param segment
     *
     * @return
     */
    public static ContractSetup buildDefaultContractSetup(Segment segment)
    {
        ContractSetup contractSetup = new ContractSetup();
        contractSetup.setSegment(segment);
        contractSetup.setAmountReceived(new EDITBigDecimal("0"));
        contractSetup.setCostBasis(new EDITBigDecimal("0"));
        contractSetup.setPolicyAmount(new EDITBigDecimal("0"));

        return contractSetup;
    }

    /**
     * Common initialization for CaseTracking transactions
     *
     * @param transactionType
     * @param masterPK
     *
     * @return
     */
    public static GroupSetup buildDefaultGroupSetup(Segment segment)
    {
        GroupSetup groupSetup = new GroupSetup();
//        groupSetup.setGroupTypeCT("INDIVIDUAL");                // individuals are now set to null
//        groupSetup.setGroupKey(segment.getContractNumber());

        return groupSetup;
    }
}
