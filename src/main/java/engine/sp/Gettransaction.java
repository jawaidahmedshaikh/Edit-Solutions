/*
 * User: sdorman
 * Date: Nov 28, 2006
 * Time: 8:55:58 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package engine.sp;

import edit.common.*;
import event.*;


/**
 * Instruction to determine if a transaction exists or not.  Does a database lookup and returns the effective date or #null.
 * <P>
 * Information received from the stack:
 * <P>
 * trxType              type of transaction
 * <P>
 * Information put on the stack after completion:
 * <P>
 * effectiveDate           string containing the effective date when transaction exists or #NULL when it doesn't
 */
public class Gettransaction extends Inst
{
    private final static String DEFAULT_OPERATOR = "System";


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

        String effectiveDate = null;
        if (trxType != null)
        {
             // Look up the transaction in the database
            effectiveDate = findTrx(trxType, segmentPK);
        }

        sp.push(effectiveDate);

        // Increment instruction pointer
        sp.incrementInstPtr();
    }

    /**
     * Looks up the transaction based on the specified parameters.  If no transactions are found, returns "#NULL".
     * The trx with the greatest effective date will be used.  If its status is not "R" the effective date will be
     * pushed to the stack else "#NULL" is returned.
     * @param transactionTypeCT
     * @param segmentPK
     * @return
     */
    public String findTrx(String transactionTypeCT, Long segmentPK)
    {
        String effectiveDate = "#NULL";

        EDITTrx[] editTrxs = EDITTrx.findBy_TransactionType_SegmentPK(transactionTypeCT, segmentPK);

        if (editTrxs != null )
        {
            EDITTrx editTrx = editTrxs[0];
            if (editTrxs.length > 1)
            {
                for (int i = 0; i < editTrxs.length; i++)
                {
                    if (editTrx.getEffectiveDate().beforeOREqual(editTrxs[i].getEffectiveDate()))
                    {
                        editTrx = editTrxs[i];
                    }
                }
            }

            if (!editTrx.getStatus().equalsIgnoreCase(EDITTrx.STATUS_REVERSAL))
            {
                effectiveDate = editTrx.getEffectiveDate().getFormattedDate();
            }
        }

        return effectiveDate;
    }
}