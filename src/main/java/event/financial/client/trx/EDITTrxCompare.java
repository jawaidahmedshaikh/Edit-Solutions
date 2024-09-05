package event.financial.client.trx;

import edit.common.vo.EDITTrxVO;
import event.common.TransactionPriorityCache;
import event.financial.client.strategy.ClientStrategy;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 8, 2003
 * Time: 4:00:05 PM
 * (c) 2000 - 2005 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */
public class EDITTrxCompare
{
    public int compare(ClientStrategy clientStrategyA, ClientStrategy clientStrategyB)
    {

        EDITTrxVO editTrxVOa = clientStrategyA.getClientTrx().getEDITTrxVO();
        EDITTrxVO editTrxVOb = clientStrategyB.getClientTrx().getEDITTrxVO();

        String statusA = clientStrategyA.getSortStatus();
        String statusB = clientStrategyB.getSortStatus();
        int compareValue = 0;

        String effectiveDateA = editTrxVOa.getEffectiveDate();
        String transactionTypeCTA = editTrxVOa.getTransactionTypeCT();
        String transactionPriorityA = padWithZero(TransactionPriorityCache.getInstance().getPriority(transactionTypeCTA));
        String sequenceNumberA = padWithZero(editTrxVOa.getSequenceNumber());

        String effectiveDateB = editTrxVOb.getEffectiveDate();
        String tranactionTypeCTB = editTrxVOb.getTransactionTypeCT();
        String transactionPriorityB = padWithZero(TransactionPriorityCache.getInstance().getPriority(tranactionTypeCTB));
        String sequenceNumberB = padWithZero(editTrxVOb.getSequenceNumber());

        if (statusA.equals(statusB) ||
            (statusA.equals("undo") && statusB.equals("natural")) ||
            (statusA.equals("undo") && statusB.equals("redo")) ||
            (statusA.equals("natural") && statusB.equals("redo")) ||
             (statusA.equals("redo") && statusB.equals("natural")))
        {
            compareValue = (effectiveDateA + transactionPriorityA + sequenceNumberA).compareTo(effectiveDateB + transactionPriorityB + sequenceNumberB);
        }
        else
        {
            compareValue = (effectiveDateB + transactionPriorityB + sequenceNumberB).compareTo(effectiveDateA + transactionPriorityA + sequenceNumberA);
        }
        return compareValue;
    }

    public int compare(EDITTrxVO editTrxVOa, EDITTrxVO editTrxVOb)
    {
        String effectiveDateA = editTrxVOa.getEffectiveDate();
        String transactionTypeCTA = editTrxVOa.getTransactionTypeCT();
        String transactionPriorityA = padWithZero(TransactionPriorityCache.getInstance().getPriority(transactionTypeCTA));
        String sequenceNumberA = padWithZero(editTrxVOa.getSequenceNumber());

        String effectiveDateB = editTrxVOb.getEffectiveDate();
        String tranactionTypeCTB = editTrxVOb.getTransactionTypeCT();
        String transactionPriorityB = padWithZero(TransactionPriorityCache.getInstance().getPriority(tranactionTypeCTB));
        String sequenceNumberB = padWithZero(editTrxVOb.getSequenceNumber());

        return (effectiveDateA + transactionPriorityA + sequenceNumberA).compareTo(effectiveDateB + transactionPriorityB + sequenceNumberB);
    }
    private String padWithZero(int number)
    {
        String returnNumber = number + "";
        if (number < 10)
        {
            returnNumber = "00" + number;
        }
        else if (number > 9 && number < 100)
        {
            returnNumber = "0" + number;
        }

        return returnNumber;
    }
}
