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
 * Instruction to determine if a transaction exists or not.  Does a database lookup and returns true or false.
 * <P>
 * Information received from the stack:
 * <P>
 * trxType              type of transaction
 * effectiveDate        effectiveDate of the transaction
 * pendingStatus        pending status
 * originatingTrxFK     fk of the originating trx
 * <P>
 * Information put on the stack after completion:
 * <P>
 * trxExists           string containing "true" or "false" that denotes whether the transaction exists or not
 */
public class Transactionexists extends Inst
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
        String effectiveDateString = (String) sp.getWSEntry("EffectiveDate");
        String trxType = (String) sp.getWSEntry("TrxType");
        String pendingStatus = (String) sp.getWSEntry("PendingStatus");
        String originatingTrxFKString = (String) sp.getWSEntry("OriginatingTrxFK");
        String segmentPKString = (String) sp.getWSEntry("SegmentPK");
        String useOriginatingTrxFK = (String) sp.getWSEntry("UseOriginatingTrxFK");  //value ='N' or 'Y'
        if (useOriginatingTrxFK == null)
        {
            useOriginatingTrxFK = "N";
        }

        Long originatingTrxFK = null;
        Long segmentPK = null;
        String trxExists = null;

        //  Convert items from stack to useful objects
        EDITDate effectiveDate = new EDITDate(effectiveDateString);

        if (useOriginatingTrxFK.equalsIgnoreCase("Y"))
        {
            if (originatingTrxFKString != null && !originatingTrxFKString.equals("") && !originatingTrxFKString.equals("null"))
            {
                originatingTrxFK = new Long(originatingTrxFKString);
            }

            // Look up the transaction in the database
            trxExists = findTrx(trxType, effectiveDate, pendingStatus, originatingTrxFK);
        }
        else
        {
            if (segmentPKString != null && !segmentPKString.equals("") && !segmentPKString.equals("null"))
            {
                segmentPK = new Long(segmentPKString);
            }

            // Look up the transaction in the database
            trxExists = findTrxWithoutOriginatingTrxFK(trxType, effectiveDate, pendingStatus, segmentPK);
        }

        sp.push(trxExists);

        // Increment instruction pointer
        sp.incrementInstPtr();
    }

    /**
     * Looks up the transaction based on the specified parameters.  If no transactions are found, returns "false",
     * otherwise returns "true".
     * @param transactionTypeCT
     * @param effectiveDate
     * @param pendingStatus
     * @param originatingTrxFK
     * @return
     */
    private String findTrx(String transactionTypeCT, EDITDate effectiveDate, String pendingStatus, Long originatingTrxFK)
    {
        String trxExists = "false";

        EDITTrx[] editTrxs = EDITTrx.findBy_TransactionType_EffectiveDate_PendingStatus_OriginatingTrxFK(transactionTypeCT, effectiveDate, pendingStatus, originatingTrxFK);

        if (editTrxs.length > 0)
        {
            trxExists = "true";
        }

        return trxExists;
    }

    private String findTrxWithoutOriginatingTrxFK(String transactionTypeCT, EDITDate effectiveDate, String pendingStatus, Long segmentPK)
    {
        String trxExists = "false";

        EDITTrx[] editTrxs = EDITTrx.findBy_TransactionType_EffectiveDate_PendingStatus_SegmentPK(transactionTypeCT, effectiveDate, pendingStatus, segmentPK);

        if (editTrxs.length > 0)
        {
            trxExists = "true";
        }

        return trxExists;
    }

}