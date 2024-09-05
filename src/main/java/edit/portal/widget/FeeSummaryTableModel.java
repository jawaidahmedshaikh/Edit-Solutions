/**
 * User: dlataill
 * Date: Sep 8, 2006
 * Time: 12:15:59 PM
 * <p/>
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package edit.portal.widget;

import java.util.Arrays;

import fission.global.AppReqBlock;
import fission.utility.Util;
import edit.portal.widgettoolkit.TableRow;
import edit.portal.widgettoolkit.TableModel;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import engine.FeeFilterRow;
import engine.FeeFilter;

import javax.servlet.http.HttpServletRequest;

public class FeeSummaryTableModel extends TableModel
{
    public static final String COLUMN_ACCT_PENDING = "Acct Pending";
    public static final String COLUMN_EFFECTIVE_DATE = "Effective Date";
    public static final String COLUMN_PROCESS_DATE = "Process Date";
    public static final String COLUMN_RELEASE_DATE = "Release Date";
    public static final String COLUMN_TRAN_TYPE = "Tran Type";
    public static final String COLUMN_STATUS = "Status";
    public static final String COLUMN_AMOUNT = "Amount";
    public static final String COLUMN_CHARGE_CODE = "Charge Code";

    private final String[] COLUMN_NAMES = {COLUMN_ACCT_PENDING, COLUMN_EFFECTIVE_DATE,
                                          COLUMN_PROCESS_DATE, COLUMN_RELEASE_DATE,
                                          COLUMN_TRAN_TYPE, COLUMN_STATUS, COLUMN_AMOUNT,
                                          COLUMN_CHARGE_CODE};

    private FeeFilterRow feeFilterRow;

    public  FeeSummaryTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    public  FeeSummaryTableModel(FeeFilterRow feeFilterRow, AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        this.feeFilterRow = feeFilterRow;

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel driven by the Segment
     */
    protected void buildTableRows()
    {
        AppReqBlock appReqBlock = this.getAppReqBlock();
        HttpServletRequest request = appReqBlock.getHttpServletRequest();

        //first get all the edit trx history
        String filteredFundPK = Util.initString(appReqBlock.getReqParm("filteredFundPK"), null);
        String filterPeriod = Util.initString(appReqBlock.getReqParm("filterPeriod"), null);
        String filterDateType = Util.initString(appReqBlock.getReqParm("filterDateType"), "EffectiveDate");
        String fromDate = Util.initString(appReqBlock.getReqParm("fromDate"), null);
        String toDate = Util.initString(appReqBlock.getReqParm("toDate"), null);
        String fromAmount = Util.initString(appReqBlock.getReqParm("fromAmount"), "0");
        String toAmount = Util.initString(appReqBlock.getReqParm("toAmount"), "0");
        String filterTransaction = Util.initString(appReqBlock.getReqParm("filterTransaction"), null);
        String sortByDateType = Util.initString(appReqBlock.getReqParm("sortByDateType"), "EffectiveDate");
        String sortOrder = Util.initString(appReqBlock.getReqParm("sortOrder"), "Ascending");

        request.setAttribute("filterPeriod", filterPeriod);
        request.setAttribute("filterDateType", filterDateType);
        request.setAttribute("fromDate", fromDate);
        request.setAttribute("toDate", toDate);
        request.setAttribute("fromAmount", fromAmount);
        request.setAttribute("toAmount", toAmount);
        request.setAttribute("filterTransaction", filterTransaction);
        request.setAttribute("sortByDateType", sortByDateType);
        request.setAttribute("sortOrder", sortOrder);

        FeeFilterRow[] feeFilterRows = null;

        if (filterPeriod == null || filterPeriod.equals("0"))
        {
            if (fromDate != null && toDate != null)
            {
                EDITDate startDate = new EDITDate(fromDate);
                EDITDate endDate   = new EDITDate(toDate);
                feeFilterRows = FeeFilter.findFeeFilterRows(new Long(filteredFundPK), filterDateType,
                                                            startDate, endDate, filterTransaction,
                                                            new EDITBigDecimal(fromAmount), new EDITBigDecimal(toAmount),
                                                            sortByDateType, sortOrder);
            }
        }
        else
        {
            feeFilterRows = FeeFilter.findFeeFilterRows(new Long(filteredFundPK), filterDateType,
                                                        filterPeriod, filterTransaction,
                                                        new EDITBigDecimal(fromAmount), new EDITBigDecimal(toAmount),
                                                        sortByDateType, sortOrder);
        }

        populateFeeFilterRows(feeFilterRows);
    }

    private void populateFeeFilterRows(FeeFilterRow[] feeFilterRows)
    {
        if (feeFilterRows != null)
        {
            for (int i = 0; i < feeFilterRows.length; i++)
            {
                TableRow tableRow = new FeeSummaryTableRow(feeFilterRows[i]);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
