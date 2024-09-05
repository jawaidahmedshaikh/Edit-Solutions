/*
 * User: gfrosti
 * Date: Feb 21, 2006
 * Time: 1:45:22 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package contract;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;

import event.EDITTrx;
import event.EDITTrxHistory;
import event.FinancialHistory;
import event.common.TransactionPriorityCache;

import fission.utility.Util;



public class HistoryFilterRow implements Comparable
{
    private String accountingPendingStatus;
    private EDITDate processDate;
    private EDITDate effectiveDate;
    private String transactionTypeCT;
    private int sequenceNumber;
    private String status;
    private EDITBigDecimal grossAmount;
    private EDITBigDecimal checkAmount;
    private String optionCodeCT;
    private String operator;
    private Long editTrxPK;
    private Long originatingTrxFK;
    private String linkedInd;

    private String key;

    public HistoryFilterRow(EDITTrxHistory editTrxHistory)
    {
        buildEDITTrxHistoryRow(editTrxHistory);

        establishKey(editTrxHistory.getClass(), editTrxHistory.getEDITTrxHistoryPK());
    }

    public HistoryFilterRow(ChangeHistory changeHistory)
    {
        buildChangeHistoryRow(changeHistory);

        establishKey(changeHistory.getClass(), changeHistory.getChangeHistoryPK());
    }

    /**
     * Build row when the driving entity is a ChangeHistory.
     */
    private void buildChangeHistoryRow(ChangeHistory changeHistory)
    {
        processDate = changeHistory.getProcessDate();
        effectiveDate = changeHistory.getEffectiveDate();
        transactionTypeCT = "NF";
        grossAmount = new EDITBigDecimal();
        checkAmount = new EDITBigDecimal();
        optionCodeCT = getOptionCodeCT(changeHistory);
        sequenceNumber = 1; // To be used for sorting.
    }

    /**
     * The Segment's optionCodeCT has to be retrieved given a Segment, ContractClient, or Investment ChangeHistory.
     * @param changeHistory String the OptionCodeCT of the related Segment.
     * @return
     */
    private String getOptionCodeCT(ChangeHistory changeHistory)
    {
        String optionCodeCT = null;

        Long segmentPK = new Long("0");

        if (changeHistory.getTableName().equalsIgnoreCase("SEGMENT") || changeHistory.getTableName().equalsIgnoreCase("Contract.Segment"))
        {
            segmentPK = changeHistory.getModifiedRecordFK();
            if (segmentPK == null)
            {
                segmentPK = changeHistory.getParentFK();
            }
        }
        else
        {
            segmentPK = changeHistory.getParentFK();
        }

        if (segmentPK != null && segmentPK != 0)
        {
            Segment segment = Segment.findByPK(segmentPK);

            if (segment != null)
            {
                optionCodeCT = segment.getOptionCodeCT();
            }
        }

        return optionCodeCT;
    }

    /**
     * A compound key of "PK:ClassName"
     *
     * @return String where the format is "PK:ClassName"
     */
    public String getKey()
    {
        return key;
    }

    /**
     * Builds a composite key for this row as "ClassName:PK".
     *
     * @return
     */
    private void establishKey(Class theClass, Long pk)
    {
        key = Util.getClassName(Util.getFullyQualifiedClassName(theClass)) + "_" + pk.toString();
    }

    /**
     * Returns the PK part of the "ClassName:PK" key.
     *
     * @param key
     * @return
     */
    public static Long getPKFromKey(String key)
    {
        String[] tokens = Util.fastTokenizer(key, "_");

        return new Long(tokens[1]);
    }

    /**
     * Returns the ClassName part of the "PK:ClassName" key.
     *
     * @param key
     * @return
     */
    public static String getClassNameFromKey(String key)
    {
        String[] tokens = Util.fastTokenizer(key, "_");

        return tokens[0];
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getAccountingPendingStatus()
    {
        return accountingPendingStatus;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITDate getProcessDate()
    {
        return processDate;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITDate getEffectiveDate()
    {
        return effectiveDate;
    }
    
    public String getOperator()
    {
        return operator;
    }
    
    public Long getEDITTrxPK()
    {
    	return editTrxPK;
    }
    
    public Long getOriginatingTrxFK()
    {
    	return originatingTrxFK;
    }
    
    public String getLinkedInd()
    {
    	return linkedInd;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getTransactionTypeCT()
    {
        return transactionTypeCT;
    }

    /**
     * Getter.
     *
     * @return
     */
    public int getSequenceNumber()
    {
        return sequenceNumber;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITBigDecimal getGrossAmount()
    {
        return grossAmount;
    }

    /**
     * Getter.
     *
     * @return
     */
    public EDITBigDecimal getCheckAmount()
    {
        return checkAmount;
    }

    /**
     * Getter.
     *
     * @return
     */
    public String getOptionCodeCT()
    {
        return optionCodeCT;
    }

    /**
     * Build row when the driving entity is an EDITTrxHistory.
     */
    private void buildEDITTrxHistoryRow(EDITTrxHistory editTrxHistory)
    {
        // EDITTrxHistory
        accountingPendingStatus = editTrxHistory.getAccountingPendingStatus();
        processDate = editTrxHistory.getProcessDateTime().getEDITDate();

        // EDITTrx
        EDITTrx editTrx = editTrxHistory.getEDITTrx();

        effectiveDate = editTrx.getEffectiveDate();
        transactionTypeCT = editTrx.getTransactionTypeCT();
        sequenceNumber = editTrx.getSequenceNumber();
        status = editTrx.getStatus();
        operator = editTrx.getOperator();
        editTrxPK = editTrx.getEDITTrxPK();
        originatingTrxFK = editTrx.getOriginatingTrxFK();

        if (transactionTypeCT.equalsIgnoreCase("PY") && originatingTrxFK != null && originatingTrxFK.compareTo(0L) > 0) {
        	linkedInd = "Y";
        } else {
        	linkedInd = "";
        }
        
        // FinancialHistory
        FinancialHistory financialHistory = editTrxHistory.getFinancialHistory();

        grossAmount = (financialHistory != null)?financialHistory.getGrossAmount():new EDITBigDecimal();
        checkAmount = (financialHistory != null)?financialHistory.getCheckAmount():new EDITBigDecimal();

        //Show the AccumulatedValue from FinancialHistory for PolicyYearEnd or CalendarYearEnd transactions
        if (transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_POLICYYEAREND)||
            transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_CALENDARYEAREND) ||
            transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_STATEMENT))
        {
            grossAmount = (financialHistory != null)?financialHistory.getAccumulatedValue():new EDITBigDecimal();
        }

        // Segment
        Segment segment = editTrx.getClientSetup().getContractSetup().getSegment();

        optionCodeCT = segment.getOptionCodeCT();
    }

    /**
     * Orders by EffectiveDate, TransactionType, SequenceNumber, and Status.
     *
     * @param object
     * @return
     */
    public int compareTo(Object object)
    {
        HistoryFilterRow visitingHistoryFilterRow = (HistoryFilterRow) object;

        // EffectiveDate
        EDITDate visitingEffectiveDate = visitingHistoryFilterRow.getEffectiveDate();
        EDITDate thisEffectiveDate = getEffectiveDate();

        // TransactionType
        String visitingTransactionpTypePriority = Util.padWithZero(String.valueOf(TransactionPriorityCache.getInstance().getPriority(visitingHistoryFilterRow.getTransactionTypeCT())), 5);
        String thisTransactionpTypePriority = Util.padWithZero(String.valueOf(TransactionPriorityCache.getInstance().getPriority(getTransactionTypeCT())), 5);

        // SequenceNumber
        String visitingSequenceNumber = Util.padWithZero(String.valueOf(visitingHistoryFilterRow.getSequenceNumber()), 5);
        String thisSequenceNumber = Util.padWithZero(String.valueOf(getSequenceNumber()), 5);

        // Status
        String visitingStatus = mapStatusToInt(visitingHistoryFilterRow.getStatus());
        String thisStatus = mapStatusToInt(getStatus());

        // Build a comparable sort String.
        String visitingSortString = visitingEffectiveDate.getFormattedDate() + ":" + visitingTransactionpTypePriority + ":" + visitingSequenceNumber + ":" + visitingStatus;
        String thisSortString = thisEffectiveDate.getFormattedDate() + ":" + thisTransactionpTypePriority + ":" + thisSequenceNumber + ":" + thisStatus;

        return thisSortString.compareTo(visitingSortString);
    }

    /**
     * The user wished to order the Status(es) ascending as follows:
     * R, U, A, N, G. The will be mapped to 01, 02, 03, 04, 05 respectively.
     * @param status
     * @return
     */
    private String mapStatusToInt(String status)
    {
        status = Util.initString(status, "");

        String statusNumber = null;

        if (status.equalsIgnoreCase("R"))
        {
            statusNumber = "01";
        }
        else if (status.equalsIgnoreCase("U"))
        {
            statusNumber = "02";
        }
        else if (status.equalsIgnoreCase("A"))
        {
            statusNumber = "03";
        }
        else if (status.equalsIgnoreCase("N"))
        {
            statusNumber = "04";
        }
        else if (status.equalsIgnoreCase("G"))
        {
            statusNumber = "05";
        }
        else
        {
            statusNumber = "";
        }

        return statusNumber;
    }
}
