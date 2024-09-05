package edit.portal.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import contract.HistoryFilter;
import contract.HistoryFilterRow;
import edit.common.vo.EDITServicesConfig;
import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import edit.services.config.ServicesConfig;
import fission.beans.PageBean;
import fission.global.AppReqBlock;
import fission.utility.Util;


public class BatchReversalTableModel extends TableModel
{
    public static final String COLUMN_ACCT_PENDING   = "Acct Pend";
    public static final String COLUMN_EFFECTIVE_DATE = "Eff Date";
    public static final String COLUMN_PROCESS_DATE   = "Proc Date";
    public static final String COLUMN_TRAN_TYPE      = "Tran Type";
    public static final String COLUMN_SEQUENCE       = "Seq";
    public static final String COLUMN_LINKED_TRX       = "Linked Trx";
    public static final String COLUMN_STATUS         = "Status";
    public static final String COLUMN_GROSS_AMOUNT   = "Gross Amt";
    public static final String COLUMN_CHECK_AMOUNT   = "Check Amt";
    public static final String COLUMN_OPTION         = "Cvg";

    private static final String[] COLUMN_NAMES = {COLUMN_ACCT_PENDING, COLUMN_EFFECTIVE_DATE, COLUMN_PROCESS_DATE,
                                                  COLUMN_TRAN_TYPE, COLUMN_SEQUENCE, COLUMN_LINKED_TRX, COLUMN_STATUS, COLUMN_GROSS_AMOUNT,
                                                  COLUMN_CHECK_AMOUNT, COLUMN_OPTION};


    private HistoryFilterRow historyFilterRow;

    public  BatchReversalTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    public  BatchReversalTableModel(HistoryFilterRow historyFilterRow, AppReqBlock appReqBlock)
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

        //first get all the edittrx history
        PageBean contractMain = appReqBlock.getSessionBean("contractMainSessionBean").getPageBean("formBean");
        String segmentPK = contractMain.getValue("segmentPK");
        String filterTransaction = Util.initString(request.getParameter("filterTransaction"), null);
        String statusRestriction = Util.initString(request.getParameter("statusRestriction"), "Undo");

        request.setAttribute("filterTransaction", filterTransaction);
        request.setAttribute("statusRestriction", statusRestriction);
        
        boolean excludeUndo = false;
        if (statusRestriction.equalsIgnoreCase("Undo"))
        {
            excludeUndo = true;
        }
        
        // Trx that users can't reverse manually
        List<String> trxToIgnore = new ArrayList<>();
        trxToIgnore.add("IS");
        trxToIgnore.add("SB");
        trxToIgnore.add("ADC");
        trxToIgnore.add("BC");
        trxToIgnore.add("CC");
        trxToIgnore.add("FI");
        trxToIgnore.add("LB");
        trxToIgnore.add("LC");
        trxToIgnore.add("LP");
        trxToIgnore.add("MA");
        trxToIgnore.add("MI");
        trxToIgnore.add("MV");
        trxToIgnore.add("PE");
        trxToIgnore.add("ST");
        
        HistoryFilterRow[] historyFilterRows = HistoryFilter.findBatchReversalHistoryRows(new Long(segmentPK), filterTransaction, excludeUndo, trxToIgnore);

        populateHistoryFilterRows(historyFilterRows);
    }

    private void populateHistoryFilterRows(HistoryFilterRow[] historyFilterRows)
    {
        if (historyFilterRows != null)
        {
            for (int i = historyFilterRows.length-1; i >= 0 ; i--)
            {
                TableRow tableRow = new BatchReversalTableRow(historyFilterRows[i]);

                if (tableRow.getRowId().equals(this.getSelectedRowId()))
                {
                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
                }

                super.addRow(tableRow);
            }
        }
    }
}
