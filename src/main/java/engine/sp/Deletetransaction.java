/*
 * User: sdorman
 * Date: Jul 29, 2008
 * Time: 8:55:58 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */
package engine.sp;

import event.*;
import logging.*;
import edit.services.logging.*;
import edit.services.db.hibernate.*;


/**
 * Instruction to delete a pending transaction with the given segmentPK and trxType.
 *
 * Information received from the stack:
 * <P>
 * trxType              type of transaction
 * segmentPK            primary key of segment
 * <P>
 * Information put on the stack after completion:
 * <P>
 * none
 */
public class Deletetransaction extends Inst
{
    public void compile(ScriptProcessor aScriptProcessor) throws SPException
    {
        sp = aScriptProcessor;  // Save instance of ScriptProcessor

        // Note: No compiling is required for this instruction
    }

    public void exec(ScriptProcessor execSP) throws SPException
    {
        sp = execSP;

        //  Remove the items from the data stack
        String trxType = (String) sp.getWSEntry("TrxType");
        String segmentPKString = (String) sp.getWSEntry("SegmentPK");

        Long segmentPK = null;

        if (segmentPKString != null && !segmentPKString.equals("") && !segmentPKString.equals("null"))
        {
            segmentPK = new Long(segmentPKString);
        }

        if (trxType != null)
        {
            deleteTrx(segmentPK, trxType);
        }

        // Increment instruction pointer
        sp.incrementInstPtr();
    }

    /**
     * Looks up the transaction based on the specified parameters and a pendingStatus of "P".
     *
     * @param segmentPK
     * @param transactionTypeCT
     *
     * @return  array of EDITTrx objects meeting the criteria
     */
    public EDITTrx[] findTransactions(Long segmentPK, String transactionTypeCT)
    {
        EDITTrx[] editTrxs = EDITTrx.findBy_TransactionType_SegmentPK_PendingStatus(transactionTypeCT, segmentPK, "P");

        return editTrxs;
    }

    /**
     * Deletes all found transactions and their associations (i.e. complete removal).
     *
     * @param segmentPK
     * @param transactionTypeCT
     */
    public void deleteTrx(Long segmentPK, String transactionTypeCT)
    {
        EDITTrx[] editTrxs = findTransactions(segmentPK, transactionTypeCT);

        if (editTrxs != null)
        {
            for (int i = 0; i < editTrxs.length; i++)
            {
                EDITTrx editTrx = editTrxs[i];

                try
                {
                    SessionHelper.beginTransaction(EDITTrx.DATABASE);

                    editTrx.hDeleteWithAssociations();

                    SessionHelper.commitTransaction(EDITTrx.DATABASE);
                }
                catch (Exception e)
                {
                    SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

                    Logging.getLogger(Logging.GENERAL_EXCEPTION).error(new LogEvent(e));
                }
                finally
                {
                    SessionHelper.clearSessions();
                }
            }
        }
    }
}