/**
 * User: dlataill
 * Date: Sep 8, 2006
 * Time: 1:02:52 PM
 * <p/>
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package engine;

import edit.common.EDITDate;
import edit.common.EDITBigDecimal;
import edit.services.db.hibernate.SessionHelper;

import java.util.*;

public class FeeFilter 
{
    /**
     * A convenvience method for the "other" findFeeFilterRows method where the specified filterPeriod will determine
     * the ultimate start/stop dates.
     *
     * @param filteredFundPK
     * @param filterDateType    Will either be EffectiveDate OR ProcessDate
     * @param filterPeriod      One of a set of values defined by the CodeTableDef of "FILTERPERIOD".
     * @param transactionTypeCT
     * @param fromAmount
     * @param toAmount
     * @param sortByDateType
     * @param sortOrder
     * @return
     * @see #findFeeFilterRows(Long, edit.common.EDITDate, edit.common.EDITDate, String)
     */
    public static FeeFilterRow[] findFeeFilterRows(Long filteredFundPK, String filterDateType, 
                                                   String filterPeriod, String transactionTypeCT,
                                                   EDITBigDecimal fromAmount, EDITBigDecimal toAmount,
                                                   String sortByDateType, String sortOrder)
    {
        EDITDate startDate = null;

        EDITDate stopDate = null;

        if (filterPeriod.equalsIgnoreCase("AllPeriods"))
        {
            startDate = new EDITDate(EDITDate.DEFAULT_MIN_DATE);

            stopDate = new EDITDate(EDITDate.DEFAULT_MAX_DATE);
        }

        else if (filterPeriod.equalsIgnoreCase("PriorWeek"))
        {
            startDate = new EDITDate().subtractDays(7);

            stopDate = new EDITDate();
        }

        else if (filterPeriod.equalsIgnoreCase("PriorMonth"))
        {
            EDITDate currentDate = new EDITDate().subtractMonths(1);
            String startMonth = currentDate.getFormattedMonth();
            String startYear = currentDate.getFormattedYear();

            startDate = new EDITDate(startYear, startMonth, "01");

            stopDate = new EDITDate(startDate.addMonths(1).subtractDays(1));
        }

        return findFeeFilterRows(filteredFundPK, filterDateType, startDate, stopDate, 
                                 transactionTypeCT, fromAmount, toAmount, sortByDateType, sortOrder);
    }

    /**
     * SegmentPK, StartDate, and StopDate are always expected. This leaves TransactionTypeCT and ExcludeUndo which yield
     * four fundamental variations of the query. Assuming SegmentPK, StartDate, StopDate are supplied, then:
     * Case 1: TransactionType == null AND ExcludeUndo = false
     * Case 2: TransactionType == null AND ExcludeUndo = true
     * Case 3: TransactionpType != null AND ExcludeUndo = false
     * Case 4: TransactionType != null AND ExcludeUndo = true
     *
     * @param filteredFundPK   required
     * @param startDate   required
     * @param stopDate    required
     * @param transactionTypeCT null or a valid Fee.TransactionTypeCT
     * @param fromAmount
     * @param toAmount
     * @param sortByDateType
     * @param sortOrder
     */
    public static FeeFilterRow[] findFeeFilterRows(Long filteredFundPK, String filterDateType,
                                                   EDITDate startDate, EDITDate stopDate,
                                                   String transactionTypeCT,
                                                   EDITBigDecimal fromAmount,
                                                   EDITBigDecimal toAmount,
                                                   String sortByDateType,
                                                   String sortOrder)
    {
        FeeFilterRow[] feeRows = null;

        Map params = buildBaseHQLParams(filteredFundPK, startDate, stopDate, fromAmount, toAmount);

        String amountQuery = "";

        if ((fromAmount.isGT("0") || fromAmount.isLT("0")) ||
            (toAmount.isGT("0") || toAmount.isLT("0")))
        {
            amountQuery = " and ((fee.TrxAmount > :fromAmount or fee.TrxAmount = :fromAmount) and (fee.TrxAmount < :toAmount or fee.TrxAmount = :toAmount))";
        }

        // Case 1 - DON'T restrict by Fee.TransactionTypeCT
        if (transactionTypeCT == null)
        {
            String hqlFee = buildHQL_Fee1(filterDateType, amountQuery, sortByDateType, sortOrder);
            feeRows = executeHQL(hqlFee, params);
        }

        // Case 2  DO restrict by EDITTrx.TransactionTypeCT
        else if (transactionTypeCT != null)
        {
            params.put("transactionTypeCT", transactionTypeCT);
            String hqlFee = buildHQL_Fee2(filterDateType, amountQuery, sortByDateType, sortOrder);
            feeRows = executeHQL(hqlFee, params);
        }

        else
        {
            String message = "FeeFilter Received Unexpected Query Parameters: [filteredFundPK = " + filteredFundPK + "] [startDate = " + startDate + "] [stopDate = " + stopDate + "] [transactionTypeCT = " + transactionTypeCT + "]";

            throw new RuntimeException(message);
        }

        return feeRows;
    }

    /**
     * Executes the specified hql and joins the results into an array of HistoryFilterRows sorted by effectiveDate ASC.
     *
     * @param hqlEDITTrxHistory
     * @return
     */
    private static FeeFilterRow[] executeHQL(String hql, Map params)
    {
        List results = SessionHelper.executeHQL(hql, params, SessionHelper.ENGINE);

        List feeRows = convertToFeeFilterRows(results);

        evictHQLResults(results);

        return (FeeFilterRow[]) feeRows.toArray(new FeeFilterRow[feeRows.size()]);
    }

    /**
     * In an attempt to keep the Hibernate Session clean, evict the current results as they are no longer needed.
     *
     * @param resultsEDITTrxHistory
     */
    private static void evictHQLResults(List resultsEDITTrxHistory)
    {
        for (int i = 0; i < resultsEDITTrxHistory.size(); i++)
        {
            Object o = resultsEDITTrxHistory.get(i);

            SessionHelper.evict(o, SessionHelper.ENGINE);
        }
    }

    /**
     * Maps the List of Fee entities to its FeeFilterRow equivalent.
     *
     * @param resultsFee
     * @return
     */
    private static List convertToFeeFilterRows(List rows)
    {
        List feeRows = new ArrayList();

        for (int i = 0; i < rows.size(); i++)
        {
            Object feeObject = rows.get(i);

            FeeFilterRow feeFilterRow = new FeeFilterRow((Fee) feeObject);

            feeRows.add(feeFilterRow);
        }

        return feeRows;
    }

    /**
     * Builds the bind variables that will be present for all hql queries.
     *
     * @param segmentPK
     * @param transactionTypeCT
     * @param startDate
     * @param stopDate
     * @param excludeUndo
     * @return
     */
    private static Map buildBaseHQLParams(Long filteredFundFK, EDITDate startDate, EDITDate stopDate,
                                          EDITBigDecimal fromAmount, EDITBigDecimal toAmount)
    {
        if (!startDate.equals(new EDITDate(EDITDate.DEFAULT_MIN_DATE)))
        {
            startDate = startDate.subtractDays(1);
        }

        if (!stopDate.equals(new EDITDate(EDITDate.DEFAULT_MAX_DATE)))
        {
            stopDate = stopDate.addDays(1);
        }

        Map params = new HashMap();
        
        params.put("filteredFundFK", filteredFundFK);

        params.put("startDate", startDate);

        params.put("stopDate", stopDate);

        if ((fromAmount.isGT("0") || fromAmount.isLT("0")) ||
            (toAmount.isGT("0") || toAmount.isLT("0")))
        {
            params.put("fromAmount", fromAmount.toString());

            params.put("toAmount", toAmount.toString());
        }

        return params;
    }

    /**
     * Includes all Fee for a [to-be] specified FilteredFund within a [to-be] specified start and
     * stop date (via Fee.EffectiveDate/Fee.ProcessDate).
     *
     * @param startDate
     * @param stopDate
     * @return
     */
    private static String buildHQL_Fee1(String filterDateType, String amountQuery, String sortByDateType, String sortOrder)
    {
        String dateType = "";
        if (filterDateType.startsWith("Process"))
        {
            dateType = "fee.ProcessDateTime";
        }
        else
        {
            dateType = "fee.EffectiveDate";
        }

        String sortDateType = "";
        if (sortByDateType.startsWith("Process"))
        {
            sortDateType = "fee.ProcessDateTime";
        }
        else
        {
            sortDateType = "fee.EffectiveDate";
        }

        if (sortOrder.startsWith("Asc"))
        {
            sortOrder = "ASC";
        }
        else
        {
            sortOrder = "DESC";
        }

        String hqlFee = "select fee from Fee fee" +
                " where " + dateType + " > :startDate  and " + dateType + " < :stopDate" +
                " and fee.FilteredFundFK = :filteredFundFK";

        if (!amountQuery.equals(""))
        {
            hqlFee = hqlFee + amountQuery;
        }

        hqlFee = hqlFee + " order by " + sortDateType + " " + sortOrder;

        return hqlFee;
    }

    /**
     * Includes all Fee for a [to-be] specified FilteredFund within a [to-be] specified
     * start and stop date (via Fee.EffectiveDate/Fee.ProcessDate).
     *
     * @param startDate
     * @param stopDate
     * @return
     */
    private static String buildHQL_Fee2(String filterDateType, String amountQuery, String sortByDateType, String sortOrder)
    {
        String dateType = "";
        if (filterDateType.startsWith("Process"))
        {
            dateType = "fee.ProcessDateTime";
        }
        else
        {
            dateType = "fee.EffectiveDate";
        }

        String sortDateType = "";
        if (sortByDateType.startsWith("Process"))
        {
            sortDateType = "fee.ProcessDateTime";
        }
        else
        {
            sortDateType = "fee.EffectiveDate";
        }

        if (sortOrder.startsWith("Asc"))
        {
            sortOrder = "ASC";
        }
        else
        {
            sortOrder = "DESC";
        }

        String hqlFee = "select fee from Fee fee" +
                " where " + dateType + " > :startDate and " + dateType + " < :stopDate" +
                " and fee.TransactionTypeCT = :transactionTypeCT" +
                " and fee.FilteredFundFK = :filteredFundFK";

        if (!amountQuery.equals(""))
        {
            hqlFee = hqlFee + amountQuery;
        }

        hqlFee = hqlFee + " order by " + sortDateType + " " + sortOrder;

        return hqlFee;
    }
}
