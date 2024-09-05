/*
 * User: cgleason
 * Date: Feb 22, 2006
 * Time: 9:07:24 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import edit.portal.widgettoolkit.*;
import edit.common.*;
import edit.common.vo.EDITServicesConfig;
import edit.services.config.ServicesConfig;


import fission.global.*;
import fission.beans.*;
import fission.utility.*;

import javax.servlet.http.*;
import java.util.*;

import contract.*;


public class ContractHistorySummaryTableModel extends TableModel
{
    public static final String COLUMN_ACCT_PENDING   = "Acct Pend";
    public static final String COLUMN_EFFECTIVE_DATE = "Eff Date";
    public static final String COLUMN_PROCESS_DATE   = "Proc Date";
    public static final String COLUMN_TRAN_TYPE      = "Tran Type";
    public static final String COLUMN_SEQUENCE       = "Seq";
    public static final String COLUMN_STATUS         = "Status";
    public static final String COLUMN_GROSS_AMOUNT   = "Gross Amt";
    public static final String COLUMN_CHECK_AMOUNT   = "Check Amt";
    public static final String COLUMN_OPTION         = "Cvg";

    private static final String[] COLUMN_NAMES = {COLUMN_ACCT_PENDING, COLUMN_EFFECTIVE_DATE, COLUMN_PROCESS_DATE,
                                                  COLUMN_TRAN_TYPE, COLUMN_SEQUENCE, COLUMN_STATUS, COLUMN_GROSS_AMOUNT,
                                                  COLUMN_CHECK_AMOUNT, COLUMN_OPTION};


    private HistoryFilterRow historyFilterRow;

    public  ContractHistorySummaryTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    public  ContractHistorySummaryTableModel(HistoryFilterRow historyFilterRow, AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        this.historyFilterRow = historyFilterRow;

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel driven by the Segment
     */
    protected void buildTableRows()
    {
        AppReqBlock appReqBlock = this.getAppReqBlock();
        HttpServletRequest request = appReqBlock.getHttpServletRequest();

        EDITServicesConfig editServicesConfig = ServicesConfig.getEditServicesConfig();
        String contractHistoryDisplay = editServicesConfig.getContractHistoryDisplay();

        //first get all the edit trx history
        PageBean contractMain = appReqBlock.getSessionBean("contractMainSessionBean").getPageBean("formBean");
        String segmentPK = contractMain.getValue("segmentPK");
        String filterTransaction = Util.initString(request.getParameter("filterTransaction"), null);
        String statusRestriction = Util.initString(request.getParameter("statusRestriction"), "");
        String fromDate = Util.initString(request.getParameter("fromDate"), null);
        String toDate = Util.initString(request.getParameter("toDate"), null);
        String filterPeriod = Util.initString(request.getParameter("filterPeriod"), "AllPeriods");

        if (filterTransaction == null && statusRestriction.equals("") &&
            fromDate == null && toDate == null && filterPeriod == null &&
            (contractHistoryDisplay != null && contractHistoryDisplay.equalsIgnoreCase("Y")))
        {
            filterPeriod = "AllPeriods";
        }

        request.setAttribute("filterTransaction", filterTransaction);
        request.setAttribute("statusRestriction", statusRestriction);
        request.setAttribute("fromDate", fromDate);
        request.setAttribute("toDate", toDate);
        request.setAttribute("filterPeriod", filterPeriod);

        boolean excludeUndo = false;
        if (statusRestriction.equalsIgnoreCase("Undo"))
        {
            excludeUndo = true;
        }

        HistoryFilterRow[] historyFilterRows = null;

        if (filterPeriod == null || filterPeriod.equals("0"))
        {
            if (fromDate != null && toDate != null)
            {
                EDITDate startDate = new EDITDate(fromDate);
                EDITDate endDate   = new EDITDate(toDate);
                historyFilterRows = HistoryFilter.findHistoryFilterRows(new Long(segmentPK), startDate, endDate, filterTransaction, excludeUndo);
            }
        }
        else
        {
            historyFilterRows = HistoryFilter.findHistoryFilterRows(new Long(segmentPK), filterPeriod, filterTransaction, excludeUndo);
        }

        populateHistoryFilterRows(historyFilterRows);
    }

    private void populateHistoryFilterRows(HistoryFilterRow[] historyFilterRows)
    {
        if (historyFilterRows != null)
        {
            for (int i = historyFilterRows.length-1; i >= 0 ; i--)
            {
                TableRow tableRow = new ContractHistorySummaryTableRow(historyFilterRows[i]);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
